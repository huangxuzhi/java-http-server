package http.server.request.body;

import http.server.exception.ParseRequestBodyException;

import java.util.Map;

public class FieldMultipart extends AbstractMultiPart {

    private String value;

    public FieldMultipart(Map<String, String> headers) throws ParseRequestBodyException {
        super(headers);
    }

    public void setValue(String value) {
        this.value = value;
    }
}
