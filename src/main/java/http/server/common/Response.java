package http.server.common;

import java.io.IOException;

public interface Response {

    public static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error";

    void write() throws IOException;
}
