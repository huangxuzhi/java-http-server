package http.server.common;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String SERVER_NAME = "Crystal";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String CONTENT_DISPOSITION = "Content-Disposition";

    public static final String FILENAME = "filename";

    public static final String UTF_8 = "UTF-8";

    public static final char SP = ' ';

    public static final char HYPHEN = '-';

    public static final char QUESTION = '?';

    public static final char AND = '&';

    public static final char EQ = '=';

    public static final char COLON = ':';

    public static final char SEMI_COLON = ';';

    public static final char CR =  '\r';

    public static final char LF =  '\n';

    public static final byte[] CRLF = {(byte)'\r', (byte)'\n'};

    public static final byte[] CRLFX2 = {(byte)'\r', (byte)'\n', (byte)'\r', (byte)'\n'};

    public static final String AND_STR = "&";

    public static final String EQ_STR = "=";

    public static final String BOUNDARY = "boundary=";

    public static final Map<String,String> DEFAULT_RESPONSE_HEADERS = new HashMap<>();

    static {
        DEFAULT_RESPONSE_HEADERS.put("Server", SERVER_NAME);
        DEFAULT_RESPONSE_HEADERS.put("Connection", "close");
    }
}
