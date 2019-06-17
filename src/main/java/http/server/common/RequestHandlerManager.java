package http.server.common;

import http.server.request.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class RequestHandlerManager {

    private String handlersPathToScan;

    private Map<String, RequestHandler> handlers = new HashMap<>(40);

    public RequestHandlerManager(String handlersPathToScan) {
        this.handlersPathToScan = handlersPathToScan;
    }

    public void registerHandlers() {

    }
}
