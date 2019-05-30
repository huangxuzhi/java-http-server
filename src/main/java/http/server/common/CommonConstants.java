package http.server.common;

import java.util.HashMap;
import java.util.Map;

public class CommonConstants {

    public static final String SERVER_NAME = "Crystal";

    public static final Map<String,String> DEFAULT_RESPONSE_HEADERS = new HashMap<>();

    static {
        DEFAULT_RESPONSE_HEADERS.put("Server", SERVER_NAME);
    }
}
