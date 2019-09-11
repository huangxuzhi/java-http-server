package http.server.request.handler;

import http.server.request.HttpRequest;
import http.server.response.HttpResponse;

public interface HttpRequestHandler {

    HttpResponse handle(HttpRequest request);
}
