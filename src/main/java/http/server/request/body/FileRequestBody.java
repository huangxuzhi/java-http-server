package http.server.request.body;

public class FileRequestBody extends AbstractRequestBody {

    public FileRequestBody() {
        super(RequestBodyType.FORM_DATA);
    }

    @Override
    public Object getContent() {
        return null;
    }
}
