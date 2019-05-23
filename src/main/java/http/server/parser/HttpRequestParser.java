package http.server.parser;

import http.server.common.HttpRequest;

public class HttpRequestParser implements Parser<HttpRequest> {

    @Override
    public HttpRequest parse(String s) {
        String requestMethod = parseRequestMethod(s);
        return null;
    }

    private String parseRequestMethod(String s) {
        return s.substring(0, s.indexOf(' '));
    }
}
