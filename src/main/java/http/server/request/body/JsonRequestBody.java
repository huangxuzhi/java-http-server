package http.server.request.body;


import http.server.common.Constants;
import http.server.exception.ParseRequestBodyException;

import java.io.InputStream;
import java.lang.reflect.Field;

public class JsonRequestBody extends AbstractHttpRequestBody {

    private byte[] json;

    public JsonRequestBody(InputStream is) {
        super(RequestBodyType.JSON, is);
    }

    @Override
    public Object getContent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initContent() throws Exception {
        json = new byte[is.available()];
        is.read(json);
    }

    public <T> T getContent(Class<T> clazz) throws Exception {
        T t = clazz.newInstance();
        if ((char)json[0] != Constants.LB || (char)json[json.length - 1] != Constants.RB) {
            throw new ParseRequestBodyException("Illegal json format");
        }
        boolean readingKey = true;
        boolean readingVal = false;
        boolean needCloseDq = false;
        StringBuilder key = new StringBuilder();
        StringBuilder val = new StringBuilder();
        for (int i = 1; i < json.length - 1; i++) {
            char c = (char)json[i];
            if (c == Constants.DQ) {
                if (!needCloseDq) {
                    needCloseDq = true;
                } else {
                    if (readingKey) {
                        readingKey = false;
                        readingVal = true;
                    } else if (readingVal) {
                        Field f = clazz.getDeclaredField(key.toString());
                        f.setAccessible(true);
                        f.set(t, val.toString());
                        key.delete(0, key.length());
                        val.delete(0, val.length());
                        readingKey = true;
                        readingVal = false;
                    }
                    needCloseDq = false;
                }
            } else if (c != Constants.COMMA && c != Constants.COLON) {
                if (readingKey) {
                    key.append(c);
                } else if (readingVal) {
                    val.append(c);
                }
            }

        }
        return t;
    }
}
