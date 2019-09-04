package http.server.request.body;

import http.server.common.Constants;
import http.server.exception.ParseRequestBodyException;
import http.server.util.ByteUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static http.server.common.Constants.CRLFX2;

public class FileRequestBody extends AbstractHttpRequestBody {

    private final byte[] boundary;

    private byte[] buffer;

    private int pos = 0;

    private List<MultiPart> parts;

    public FileRequestBody(InputStream is, String boundary) throws IOException {
        super(RequestBodyType.FORM_DATA, is);
        this.boundary = boundary.getBytes();
        this.buffer = new byte[is.available()];
        parts = new ArrayList<>();
    }

    @Override
    public Object getContent() {
        return null;
    }

    @Override
    public void initContent() throws Exception{
        try {
            byte b;
            while (true) {
                b = read();
                if (b == (byte)Constants.CR) break;
            }
            read(1);

            while (true) {
                read(2);
                byte[] boundaryEnd = getBoundaryEnd();
                if (ByteUtils.arrayEquals(boundaryEnd, 0, buffer, pos - boundaryEnd.length + 1, boundaryEnd.length)) {
                    break;
                }

                Map<String,String> headers = new HashMap<>(8);
                MultiPart part;
                boolean readingKey = true, readingVal = false;
                StringBuilder key = new StringBuilder(32).append((char)buffer[pos - 2]).append((char)buffer[pos - 1]);
                StringBuilder val = new StringBuilder(128);
                while (true) {
                    b = read();
                    if (b == (byte)Constants.COLON) {
                        readingKey = false;
                        readingVal = true;
                        continue;
                    }
                    if (b == (byte)Constants.CR) {
                        read();
                        if (key.length() > 0) {
                            headers.put(key.toString().trim(), val.toString().trim());
                        }
                        if (ByteUtils.arrayEquals(CRLFX2, 0, buffer, pos - 4, 4)) {
                            break;
                        }
                        readingKey = true;
                        readingVal = false;
                        key.delete(0, key.length());
                        val.delete(0, val.length());
                        continue;
                    }
                    if (readingKey) {
                        key.append((char)b);
                    }
                    if (readingVal) {
                        val.append((char)b);
                    }
                }

                String contentDispositon = headers.get(Constants.CONTENT_DISPOSITION);
                if (contentDispositon.contains(Constants.FILENAME)) {
                    part = new FileMultiPart(headers);
                    byte[] c = getContentForPart();
                    ((FileMultiPart) part).setBytes(c);
                } else {
                    part = new FieldMultipart(headers);
                    byte[] c = getContentForPart();
                    ((FieldMultipart) part).setValue(new String(c));
                }
                parts.add(part);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseRequestBodyException(e);
        }
    }

    private int read(int length) throws IOException {
        int r = is.read(buffer, pos, length);
        pos += length;
        return r;
    }

    private byte read() throws IOException {
        byte b = (byte)is.read();
        buffer[pos++] = b;
        return b;
    }

    private byte[] getBoundaryEnd() {
        byte[] boundaryEnd = new byte[boundary.length + 2];
        System.arraycopy(boundary, 0, boundaryEnd, 0, boundary.length);
        int len = boundaryEnd.length;
        boundaryEnd[len - 1] = Constants.HYPHEN;
        boundaryEnd[len - 2] = Constants.HYPHEN;
        return boundaryEnd;
    }

    private byte[] getContentForPart() throws IOException {
        int posT = pos;
        while (!ByteUtils.arrayEquals(boundary, 0, buffer, pos - boundary.length, boundary.length)) {
            read();
        }
        byte[] b = new byte[pos - boundary.length - posT - 4];
        System.arraycopy(buffer, posT, b, 0, b.length);
        return b;
    }

}

