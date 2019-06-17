package http.server.factory;

import http.server.common.Constants;
import http.server.response.HttpResponse;
import http.server.response.Response;

import java.io.OutputStream;

public class DefaultHttpResponseFactory {

    public static Response createResponse(OutputStream os) {
        Response response = new HttpResponse(os);
        ((HttpResponse) response).setHeaders(Constants.DEFAULT_RESPONSE_HEADERS);
        return response;
    }
}
