package http.server.common;

import java.util.Map;

public class HttpRequest {
    private String method;
    private String uri;
    private String httpVersion;
    private Map<String,String> headers;
    private Map<String,String> parameters;
    private String body;
    
}
