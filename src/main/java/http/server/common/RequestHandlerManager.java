package http.server.common;

import http.server.request.handler.MyHttpRequestHandler;

import java.util.HashMap;
import java.util.Map;

public class RequestHandlerManager {

    private String handlersPathToScan;

    private Map<String, MyHttpRequestHandler> handlers = new HashMap<>(40);

    public RequestHandlerManager(String handlersPathToScan) {
        this.handlersPathToScan = handlersPathToScan;
    }

    public void registerHandlers() {

    }
}
