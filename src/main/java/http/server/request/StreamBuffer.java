package http.server.request;

import java.io.IOException;
import java.io.InputStream;

public class StreamBuffer {

    private InputStream is;

    private int pos = 0;

    private Request request = new HttpRequest();

    public StreamBuffer(InputStream is) {
        this.is = is;
    }

    private void init() throws IOException {

    }

    private void parseRequestLine() {

    }
}
