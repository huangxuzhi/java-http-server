package http.server.request.body;

public abstract class AbstractRequestBody implements RequestBody{

    protected RequestBodyType requestBodyType;

    public AbstractRequestBody(RequestBodyType type) {
        this.requestBodyType = type;
    }


}
