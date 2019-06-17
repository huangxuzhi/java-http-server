package http.server.parser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface Parser<T, S> {

    S parse(T t) throws IOException;
}
