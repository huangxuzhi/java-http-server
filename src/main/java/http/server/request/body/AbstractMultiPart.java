package http.server.request.body;

import http.server.common.Constants;
import http.server.exception.ParseRequestBodyException;

import java.util.Map;

public abstract class AbstractMultiPart implements MultiPart{
    protected Map<String,String> headers;

    protected String name;

    public AbstractMultiPart(Map<String, String> headers) throws ParseRequestBodyException {
        this.headers = headers;
        String contentDisposition = headers.get(Constants.CONTENT_DISPOSITION);
        int idx = contentDisposition.indexOf("name=\"");
        int idx2 = contentDisposition.indexOf("filename=\"");
        if (idx < 0 && idx2 < 0) {
            throw new ParseRequestBodyException("No name defined for multipart");
        }
        idx +=6;
        int idx3 = contentDisposition.indexOf('\"', idx);
        name = contentDisposition.substring(idx, idx3);
    }
}
