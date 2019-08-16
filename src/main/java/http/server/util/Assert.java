package http.server.util;

public class Assert {

    public static void assertPositive(int i, String message) {
        if (i <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertNotNull(Object o, String message) {
        if (o == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
