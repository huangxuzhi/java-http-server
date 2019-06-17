package http.server.request.body;

public class UrlencodedRequestBody extends AbstractRequestBody {

    public UrlencodedRequestBody() {
        super(RequestBodyType.X_WWW_FORM_URLENCODED);
    }

    @Override
    public Object getContent() {
        return null;
    }
}
