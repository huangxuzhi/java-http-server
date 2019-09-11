package http.server.request.body;

import http.server.common.Constants;
import http.server.exception.ParseRequestBodyException;

import java.io.*;
import java.util.Map;

public class FilePart extends AbstractPart {

    private byte[] bytes;

    private String fileName;

    public FilePart(Map<String, String> headers) throws ParseRequestBodyException {
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

    public void write(File f) throws IOException {
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }

    public void write(String filePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
