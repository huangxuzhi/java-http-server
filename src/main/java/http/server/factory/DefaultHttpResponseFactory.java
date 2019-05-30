package http.server.factory;

import http.server.common.CommonConstants;
import http.server.common.HttpResponse;
import http.server.common.Response;

import java.io.OutputStream;

public class DefaultHttpResponseFactory {

    public static Response createResponse(OutputStream os) {
        Response response = new HttpResponse(os);
        ((HttpResponse) response).setHeaders(CommonConstants.DEFAULT_RESPONSE_HEADERS);
        return response;
    }
}
