package http.server.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String SERVER_NAME = "Crystal";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final Map<String,String> DEFAULT_RESPONSE_HEADERS = new HashMap<>();

    static {
        DEFAULT_RESPONSE_HEADERS.put("Server", SERVER_NAME);
        DEFAULT_RESPONSE_HEADERS.put("Connection", "close");
    }
}
