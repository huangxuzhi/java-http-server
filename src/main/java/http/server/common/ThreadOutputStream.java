package http.server.common;

import java.io.OutputStream;

public class ThreadOutputStream {

    public static final ThreadLocal<OutputStream> OUTPUT_STREAM = new ThreadLocal<>();

    public static ThreadOutputStream getInstance() {
        return ThreadOutputStreamHolder.instance;
    }

    public static class ThreadOutputStreamHolder {
        public static ThreadOutputStream instance = new ThreadOutputStream();
    }
}
