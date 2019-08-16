package http.server.parser;

import http.server.request.Request;

public abstract class RequestParser<S, T extends Request> {

    protected S s;

    public RequestParser(S s) {
        this.s = s;
    }

    public abstract T parse() throws Exception;
}
