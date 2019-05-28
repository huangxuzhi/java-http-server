package http.server.parser;

import http.server.common.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser implements Parser<HttpRequest> {

    private HttpRequestParser(){}

    public static HttpRequestParser instance = new HttpRequestParser();

    public static HttpRequestParser getInstance() {
        return instance;
    }

    @Override
    public HttpRequest parse(String s) {
        HttpRequest request = new HttpRequest();
        String requestLine = s.substring(0, s.indexOf("\r\n"));
        parseRequestLine(requestLine, request);
        String requestHeaders = s.substring(s.indexOf("\r\n") + 2, s.indexOf("\r\n\r\n"));
        parseRequestHeader(requestHeaders, request);
        String body = s.substring(s.indexOf("\r\n\r\n") + 4);
        request.setBody(body);
        return request;
    }

    private void parseRequestLine(String requestLine, HttpRequest request) {
        String[] arr = requestLine.split(" ");
        request.setMethod(arr[0]);
        String[] uriAndParameters = arr[1].split("\\?");
        Map<String,String> params = new HashMap<>();
        if (uriAndParameters.length > 1) {
            String[] parameters = uriAndParameters[1].split("&");
            for (int i = 0; i < parameters.length; i++) {
                String[] keyAndValue = parameters[i].split("=");
                if (keyAndValue.length > 1) {
                    params.put(keyAndValue[0], keyAndValue[1]);
                }
            }

        }
        request.setUri(uriAndParameters[0]);
        request.setHttpVersion(arr[2]);
    }

    private void parseRequestHeader(String requestHeaders, HttpRequest request) {
        Map<String,String> headers = new HashMap<>(30);
        while (true) {
            int idx = requestHeaders.indexOf("\r\n");
            if (idx > 0) { // means next line exists
                String h = requestHeaders.substring(0, idx);
                String[] arr = h.split(":");
                headers.put(arr[0], arr[1]);
                requestHeaders = requestHeaders.substring(idx + 2);
            } else { // it is the last line
                String h = requestHeaders;
                String[] arr = h.split(":");
                headers.put(arr[0], arr[1]);
                break;
            }
        }
        request.setHeaders(headers);
    }
}
