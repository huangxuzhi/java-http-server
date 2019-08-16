package http.server.request.body;

import http.server.common.Constants;
import http.server.exception.ParseRequestBodyException;

import java.io.OutputStream;
import java.util.Map;

public class FileMultiPart extends AbstractMultiPart {

    private OutputStream os;

    private String fileName;

    public FileMultiPart(Map<String, String> headers) throws ParseRequestBodyException {
        super(headers);
        String contentDisposition = headers.get(Constants.CONTENT_DISPOSITION);
        int idx = contentDisposition.indexOf("filename=\"");
        if (idx < 0) {
            throw new ParseRequestBodyException("No filename defined for file multipart");
        }
        idx += 11;
        int idx2 = contentDisposition.indexOf('\"', idx);
        fileName = contentDisposition.substring(idx, idx2);

    }

    public void setOs(OutputStream os) {
        this.os = os;
    }
}
