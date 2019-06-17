package http.server.response;

import http.server.common.Constants;
import http.server.request.Request;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponse implements Response{

    private String httpVersion = Request.HTTP_VERSION;
    private HttpStatus httpStatus;
    private Map<String,String> headers;
    private String body = "";
    private OutputStream os;

    public HttpResponse(OutputStream os) {
        this.os = os;
    }

    @Override
    public void write() throws IOException {
        byte[] buffer = toString().getBytes();
        os.write(buffer);
        os.flush();
        os.close();
    }

    @Override
    public String toString() {
        if (httpStatus == null || headers == null || headers.size() <= 0) {
            httpStatus = HttpStatus.Internal_Server_Error;
            body = INTERNAL_SERVER_ERROR_MSG;
            headers = Constants.DEFAULT_RESPONSE_HEADERS;
        }
        StringBuilder sb = new StringBuilder(1500);
        sb.append(httpVersion).append(" ").append(httpStatus.getCode()).append(" ").append(httpStatus.getStatus()).append("\r\n");
        for (Map.Entry<String,String> e : headers.entrySet()) {
            sb.append(e.getKey()).append(":").append(e.getValue()).append("\r\n");
        }
        sb.append("\r\n\r\n");
        sb.append(body);
        return sb.toString();
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
