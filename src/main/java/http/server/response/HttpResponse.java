package http.server.response;

import http.server.common.Constants;
import http.server.common.ThreadOutputStream;
import http.server.factory.HttpResponseFactory;
import http.server.request.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse implements Response{

    private String httpVersion = HttpRequest.HTTP_VERSION;
    private HttpStatus httpStatus;
    private Map<String,String> headers;
    private String body = "";

    @Override
    public void write() throws IOException {
        byte[] buffer = toString().getBytes();
        OutputStream os = ThreadOutputStream.OUTPUT_STREAM.get();
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
        sb.append("\r\n");
        sb.append(body);
        return sb.toString();
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String name, String value) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.put(name, value);
    }

    public void setHttpStatus(HttpStatus status) {
        this.httpStatus = status;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private HttpResponse response;

        public Builder() {
            response = HttpResponseFactory.baseResponse();
        }

        HttpResponse status(HttpStatus status) {
            response.httpStatus = status;
            return response;
        }

        HttpResponse addHeader(String name, String value) {
            response.addHeader(name, value);
            return response;
        }

        HttpResponse body(String body) {
            response.setBody(body);
            return response;
        }

        HttpResponse getResponse() {
            return response;
        }
    }
}
