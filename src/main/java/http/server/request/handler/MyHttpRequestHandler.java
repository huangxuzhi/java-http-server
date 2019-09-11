package http.server.request.handler;

import http.server.factory.HttpResponseFactory;
import http.server.request.HttpRequest;
import http.server.response.HttpResponse;

public class MyHttpRequestHandler implements HttpRequestHandler {

    public HttpResponse handle(HttpRequest request) {
        String method = request.getMethod();
        String uri = request.getUri();
        System.out.println("得到请求:method=" + method + ",uri=" + uri);
        return HttpResponseFactory.textResponse();
    }
}
