package http.server.request.body;

import java.io.InputStream;

public abstract class AbstractHttpRequestBody implements HttpRequestBody {

    protected RequestBodyType requestBodyType;

    protected InputStream is;

    public AbstractHttpRequestBody(RequestBodyType type, InputStream is) {
        this.requestBodyType = type;
        this.is = is;
    }


}
