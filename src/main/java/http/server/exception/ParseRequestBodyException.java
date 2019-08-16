package http.server.exception;

public class ParseRequestBodyException extends Exception {

    public ParseRequestBodyException(Throwable cause) {
        super(cause);
    }

    public ParseRequestBodyException(String message) {
        super(message);
    }
}
