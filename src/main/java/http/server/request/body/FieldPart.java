package http.server.request.body;

import http.server.exception.ParseRequestBodyException;

import java.util.Map;

public class FieldPart extends AbstractPart {

    private String value;

    public FieldPart(Map<String, String> headers) throws ParseRequestBodyException {
        super(headers);
    }

    public void setValue(String value) {
        this.value = value;
    }
}
