package http.server.request.body;

import java.io.IOException;

public interface HttpRequestBody {

    Object getContent();

    void initContent() throws IOException;

}
