package http.server.request.body;

import http.server.common.Constants;
import http.server.exception.ParseRequestBodyException;
import http.server.util.ByteUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static http.server.common.Constants.CRLFX2;

public class FileRequestBody extends AbstractHttpRequestBody {

    private final byte[] boundary;

    private byte[] buffer;

    private int pos = 0;

    private Map<String, Part> parts;

    public FileRequestBody(InputStream is, String boundary) throws IOException {
        super(RequestBodyType.FORM_DATA, is);
        this.boundary = boundary.getBytes();
        this.buffer = new byte[is.available()];
        parts = new HashMap<>();
    }

    @Override
    public Object getContent() {
        return null;
    }

    @Override
    public void initContent() throws Exception{
        try {
            byte b;
            // 读完第一个part的boundary
            while (true) {
                b = read();
                if (b == (byte)Constants.CR) break;
            }
            read(1);

            while (true) {
                // 因为最后结尾是boundary+"--"，所以这里多读两个字符检查是否到达结尾，如果是就跳出，解析结束
                read(2);
                byte[] boundaryEnd = getBoundaryEnd();
                if (ByteUtils.arrayEquals(boundaryEnd, 0, buffer, pos - boundaryEnd.length + 1, boundaryEnd.length)) {
                    break;
                }

                Map<String,String> headers = new HashMap<>(8);
                Part part;
                boolean readingKey = true, readingVal = false;
                byte[] key = new byte[32];
                byte[] val = new byte[128];
                int keyIdx = 0, valIdx = 0;
                key[keyIdx++] = buffer[pos - 2];
                key[keyIdx++] = buffer[pos - 1];
                while (true) {
                    b = read();
                    if (b == (byte)Constants.COLON) {
                        readingKey = false;
                        readingVal = true;
                        continue;
                    }
                    if (b == (byte)Constants.CR) {
                        read();
                        if (keyIdx > 0) {
                            byte[] keyTmp = new byte[keyIdx];
                            byte[] valTmp = new byte[valIdx];
                            System.arraycopy(key, 0, keyTmp, 0, keyIdx);
                            System.arraycopy(val, 0, valTmp, 0, valIdx);
                            headers.put(new String(keyTmp, Constants.UTF_8).trim(), new String(valTmp, Constants.UTF_8).trim());
                            readingKey = true;
                            readingVal = false;
                            keyIdx = 0;
                            valIdx = 0;
                            continue;
                        }
                        if (ByteUtils.arrayEquals(CRLFX2, 0, buffer, pos - 4, 4)) {
                            break;
                        }
                    }
                    if (readingKey) {
                        key[keyIdx++] = b;
                    }
                    if (readingVal) {
                        val[valIdx++] = b;
                    }
                }

                String contentDispositon = headers.get(Constants.CONTENT_DISPOSITION);
                if (contentDispositon.contains(Constants.FILENAME)) {
                    part = new FilePart(headers);
                    byte[] c = getContentForPart();
                    ((FilePart) part).setBytes(c);
                } else {
                    part = new FieldPart(headers);
                    byte[] c = getContentForPart();
                    ((FieldPart) part).setValue(new String(c));
                }
                parts.put(headers.get(Constants.NAME), part);

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

