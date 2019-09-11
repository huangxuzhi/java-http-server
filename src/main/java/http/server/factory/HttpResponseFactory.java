package http.server.factory;

import http.server.common.Constants;
import http.server.common.MIMEType;
import http.server.response.HttpResponse;
import http.server.response.HttpStatus;
import http.server.response.Response;

import java.io.OutputStream;

public class HttpResponseFactory {

    public static HttpResponse baseResponse() {
        HttpResponse response = new HttpResponse();
        response.setHeaders(Constants.DEFAULT_RESPONSE_HEADERS);
        return response;
    }

    public static HttpResponse textResponse() {
        HttpResponse response =  baseResponse();
        response.setHttpStatus(HttpStatus.OK);
        response.addHeader(Constants.CONTENT_TYPE, MIMEType.PLAIN.getTypeName());
        return response;
    }

    public static HttpResponse jsonResponse() {
        HttpResponse response =  baseResponse();
        response.setHttpStatus(HttpStatus.OK);
        response.addHeader(Constants.CONTENT_TYPE, MIMEType.JSON.getTypeName());
        return response;
    }

    public static HttpResponse htmlResponse() {
        HttpResponse response =  baseResponse();
        response.setHttpStatus(HttpStatus.OK);
        response.addHeader(Constants.CONTENT_TYPE, MIMEType.HTML.getTypeName());
        return response;
    }

    public static HttpResponse badRequestResponse() {
        HttpResponse response =  baseResponse();
        response.setHttpStatus(HttpStatus.Bad_Request);
        return response;
    }

    public static HttpResponse internalServerErrorResponse() {
        HttpResponse response =  baseResponse();
        response.setHttpStatus(HttpStatus.Internal_Server_Error);
        return response;
    }
}
