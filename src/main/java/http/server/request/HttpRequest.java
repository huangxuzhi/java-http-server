package http.server.request;

import http.server.request.body.HttpRequestBody;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements Request{
    private String method;
    private String uri;
    private String httpVersion;
    private Map<String,String> headers;
    private Map<String,String> parameters;
    private HttpRequestBody body;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String name) {
        String header = null;
        if (headers != null) {
            header = headers.get(name);
        }
        return header;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setBody(HttpRequestBody body) {
        this.body = body;
    }

    public HttpRequestBody getBody() {
        return body;
    }

    @Override
    public void addParameter(String key, String val) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        parameters.put(key, val);
    }

    public void addHeader(String key, String val) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, val);
    }
}
