package http.server.request.body;

import java.io.InputStream;

public class FileRequestBody extends AbstractHttpRequestBody {



    public FileRequestBody(InputStream is) {
        super(RequestBodyType.FORM_DATA, is);
    }

    @Override
    public Object getContent() {
        return null;
    }

    @Override
    public void initContent() {

    }
}

