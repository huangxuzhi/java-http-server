package http.server.request;

import http.server.common.Constants;
import http.server.common.MIMEType;
import http.server.exception.ContentTypeNotSupportedException;
import http.server.request.body.FileRequestBody;
import http.server.request.body.HttpRequestBody;
import http.server.request.body.TextRequestBody;
import http.server.request.body.UrlencodedRequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class StreamBuffer {

    public static final char SP = ' ';

    public static final char QUESTION = '?';

    public static final char AND = '&';

    public static final char EQ = '=';

    public static final char COLON = ':';

    public static final char CR =  '\r';

    public static final char LF =  '\n';

    public static final String CRLF = "\r\n";

    private InputStream is;

    private int pos = 0;

    private HttpRequest request = new HttpRequest();

    public StreamBuffer(InputStream is) {
        this.is = is;
    }

    private void parseRequestLine() throws IOException {
        char c;
        boolean readingUri = true;
        boolean readingKey = false;
        boolean readingVal = false;
        StringBuilder method = new StringBuilder(7);
        StringBuilder uri = new StringBuilder(128);
        StringBuilder version = new StringBuilder(16);
        StringBuilder key = null;
        StringBuilder val = null;

        while ((c = (char)is.read()) != SP) {
            method.append(c);
        }
        request.setMethod(method.toString());

        while ((c = (char)is.read()) != SP) {
            if (c == QUESTION) {
                request.setUri(uri.toString());
                readingKey = true;
                readingUri = false;
                continue;
            }
            if (c == EQ) {
                readingKey = false;
                readingVal = true;
                continue;
            }
            if (c == AND) {
                request.addParameter(key.toString(), val.toString());
                key.delete(0, key.length());
                val.delete(0, val.length());
                readingKey = true;
                readingVal = false;
                continue;
            }
            if (readingUri) {
                uri.append(c);
            }
            if (readingKey) {
                if (key == null) {
                    key = new StringBuilder(64);
                }
                key.append(c);
            }
            if (readingVal) {
                if (val == null) {
                    val = new StringBuilder(64);
                }
                val.append(c);
            }
        }

        while ((c = (char)is.read()) != CR) {
            version.append(c);
        }
        request.setHttpVersion(version.toString());
        is.skip(1);
    }

    private void parseRequestHeaders() throws IOException {
        char c;
        boolean readingHeaderKey = true;
        boolean readingHeaderVal = false;
        boolean crlfAlready = false;
        StringBuilder key = new StringBuilder(32);
        StringBuilder val = new StringBuilder(32);
        while ((c = (char)is.read()) != CR || !crlfAlready) {
            if (crlfAlready) {
                crlfAlready = false;
            }
            if(c == COLON && readingHeaderKey) {
                readingHeaderKey = false;
                readingHeaderVal = true;
                continue;
            }
            if (c == CR) {
                readingHeaderKey = true;
                readingHeaderVal = false;
                request.addHeader(key.toString().trim(), val.toString().trim());
                key.delete(0,key.length());
                val.delete(0, val.length());
                is.skip(1);
                crlfAlready = true;
                continue;
            }
            if (readingHeaderKey) {
                key.append(c);
            }
            if (readingHeaderVal) {
                val.append(c);
            }
        }
        is.skip(1);
    }

    private void parseRequestBody() throws ContentTypeNotSupportedException {
        Map<String,String> headers;
        if ( (headers = request.getHeaders()) != null && headers.containsKey(Constants.CONTENT_TYPE)) {
            String ct = null;
            MIMEType type = null;
            HttpRequestBody body;
            try {
                ct = headers.get(Constants.CONTENT_TYPE);
                type = MIMEType.valueOf(ct);
            } catch (EnumConstantNotPresentException e) {
                throw new ContentTypeNotSupportedException("Unable to address the '" + ct +"' content type");
            }
            switch (type) {
                case PLAIN:
                    body = new TextRequestBody(is);
                    break;
                case X_WWW_FORM_URLENCODED:
                    body = new UrlencodedRequestBody(is);
                    break;
                case FORM_DATA:
                    body = new FileRequestBody(is);
                    break;
            }

        }
    }

    public Request buildRequest() throws IOException {
        parseRequestLine();
        parseRequestHeaders();
        return request;
    }


}
