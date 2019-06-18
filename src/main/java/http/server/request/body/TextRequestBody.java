package http.server.request.body;

import http.server.common.ServerConfig;

import java.io.IOException;
import java.io.InputStream;

public class TextRequestBody extends AbstractHttpRequestBody {

    private String content;

    public TextRequestBody(InputStream is) {
        super(RequestBodyType.PLAIN, is);
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void initContent() throws IOException {
        byte[] b = new byte[is.available()];
        is.read(b);
        content = new String(b, ServerConfig.DEFAULT_CHARSET);
    }
}
