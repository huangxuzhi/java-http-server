package http.server.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponse {

    private String httpVersion = HttpRequest.HTTP_VERSION;
    private HttpStatus httpStatus;
    private Map<String,String> headers;
    private String body;
    private OutputStream os;

    public HttpResponse(OutputStream os) {
        this.os = os;
    }

    @Override
    public String toString() {
        if (httpStatus == null) {
            httpStatus = HttpStatus.Internal_Server_Error;
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

    public void write() throws IOException {
        byte[] buffer = toString().getBytes();
        os.write(buffer);
        os.flush();
        os.close();
    }
}
