package http.server.util;

public class StringUtils {

    public static boolean hasText(String s) {
        return s != null && s.trim().length() > 0;
    }

}
