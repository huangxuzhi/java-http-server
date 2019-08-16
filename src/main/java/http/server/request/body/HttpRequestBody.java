package http.server.request.body;



public interface HttpRequestBody {

    Object getContent();

    void initContent() throws Exception;

}
