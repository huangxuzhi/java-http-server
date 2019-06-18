package http.server.request;

public interface Request {

    public static final String HTTP_VERSION = "HTTP/1.1";

    void addParameter(String key, String val);
}
