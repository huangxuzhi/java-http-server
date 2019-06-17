package http.server.request.body;

public class TextRequestBody extends AbstractRequestBody {

    public TextRequestBody() {
        super(RequestBodyType.PLAIN);
    }

    @Override
    public String getContent() {
        return null;
    }
}
