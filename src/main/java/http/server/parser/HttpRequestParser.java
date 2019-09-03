package http.server.parser;

import http.server.common.Constants;
import http.server.common.MIMEType;
import http.server.exception.ContentTypeNotSupportedException;
import http.server.request.HttpRequest;
import http.server.request.body.*;

import java.io.*;
import java.util.Map;

public class HttpRequestParser extends RequestParser<InputStream, HttpRequest> {

    private HttpRequest request;

    public HttpRequestParser(InputStream is) {
        super(is);
        this.request = new HttpRequest();
    }

    @Override
    public HttpRequest parse() throws Exception {
        parseRequestLine();
        parseRequestHeaders();
        parseRequestBody();
        return getRequest();
    }

    public HttpRequest getRequest() {
        return this.request;
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

        while ((c = (char)s.read()) != Constants.SP) {
            method.append(c);
        }
        request.setMethod(method.toString());

        while ((c = (char)s.read()) != Constants.SP) {
            if (c == Constants.QUESTION) {
                request.setUri(uri.toString());
                readingKey = true;
                readingUri = false;
                continue;
            }
            if (c == Constants.EQ) {
                readingKey = false;
                readingVal = true;
                continue;
            }
            if (c == Constants.AND) {
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

        while ((c = (char)s.read()) != Constants.CR) {
            version.append(c);
        }
        request.setHttpVersion(version.toString());
        s.skip(1);
    }

    private void parseRequestHeaders() throws Exception {
        char c;
        boolean readingHeaderKey = true;
        boolean readingHeaderVal = false;
        boolean crlfAlready = false;
        StringBuilder key = new StringBuilder(32);
        StringBuilder val = new StringBuilder(32);
        while ((c = (char)s.read()) != Constants.CR || !crlfAlready) {
            if (crlfAlready) {
                crlfAlready = false;
            }
            if(c == Constants.COLON && readingHeaderKey) {
                readingHeaderKey = false;
                readingHeaderVal = true;
                continue;
            }
            if (c == Constants.CR) {
                readingHeaderKey = true;
                readingHeaderVal = false;
                request.addHeader(key.toString().trim(), val.toString().trim());
                key.delete(0,key.length());
                val.delete(0, val.length());
                s.skip(1);
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
        s.skip(1);
    }

    private void parseRequestBody() throws Exception {
        Map<String,String> headers;
        if ( (headers = request.getHeaders()) != null && headers.containsKey(Constants.CONTENT_TYPE)) {
            String ct = null;
            MIMEType type = null;
            HttpRequestBody body = null;
            try {
                ct = headers.get(Constants.CONTENT_TYPE);
                int semiColonIdx = ct.indexOf(Constants.SEMI_COLON);
                type = MIMEType.getByTypeName(ct.substring(0, semiColonIdx > 0 ? semiColonIdx : ct.length()));
            } catch (EnumConstantNotPresentException e) {
                throw new ContentTypeNotSupportedException("Unable to address the '" + ct +"' content type");
            }
            switch (type) {
                case PLAIN:
                    body = new TextRequestBody(s);
                    break;
                case X_WWW_FORM_URLENCODED:
                    body = new UrlencodedRequestBody(s);
                    break;
                case FORM_DATA:
                    body = new FileRequestBody(s, ct.substring(ct.indexOf(Constants.BOUNDARY) + 10));
                    break;
                case JSON:
                    body = new JsonRequestBody(s);
                default:
                    break;
            }
            if (body != null) {
                body.initContent();
                request.setBody(body);
            }
        }
    }
}
