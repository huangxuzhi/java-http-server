package http.server.request.body;

import java.io.InputStream;

public class UrlencodedRequestBody extends AbstractHttpRequestBody {

    public UrlencodedRequestBody(InputStream is) {
        super(RequestBodyType.X_WWW_FORM_URLENCODED, is);
    }

    @Override
    public Object getContent() {
        return null;
    }
}
