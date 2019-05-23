package http.server.parser;

public interface Parser<T> {

    T parse(String s);
}
