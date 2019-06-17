package http.server.parser;

import http.server.request.HttpRequest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser implements Parser<InputStream, HttpRequest> {

    public static final String DEFAULT_CHARSET = "UTF-8";

    private String charset;

    private HttpRequestParser(String charset){
        this.charset = charset;
    }

    @Override
    public HttpRequest parse(InputStream is) throws IOException {
        HttpRequest request = new HttpRequest();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String requestLine = reader.readLine();
        parseRequestLine(requestLine, request);
        String s;
        while ((s = reader.readLine()) != null) {
            if ("".equals(s)) break;
            System.out.println(s);
        }
        System.out.println(is.available());
        byte[] b = new byte[is.available()];
        is.read(b);
        System.out.println(new String(b));
        return request;
    }

    private void parseRequestLine(String requestLine, HttpRequest request) {
        String[] arr = requestLine.split(" ");
        request.setMethod(arr[0]);
        String[] uriAndParameters = arr[1].split("\\?");
        Map<String,String> params = new HashMap<>();
        if (uriAndParameters.length > 1) {
            String[] parameters = uriAndParameters[1].split("&");
            for (int i = 0; i < parameters.length; i++) {
                String[] keyAndValue = parameters[i].split("=");
                if (keyAndValue.length > 1) {
                    params.put(keyAndValue[0], keyAndValue[1]);
                }
            }

        }
        request.setUri(uriAndParameters[0]);
        request.setHttpVersion(arr[2]);
    }

    private void parseRequestHeader(String requestHeaders, HttpRequest request) {
        Map<String,String> headers = new HashMap<>(30);
        while (true) {
            int idx = requestHeaders.indexOf("\r\n");
            if (idx > 0) { // means next line exists
                String h = requestHeaders.substring(0, idx);
                String[] arr = h.split(":");
                headers.put(arr[0], arr[1]);
                requestHeaders = requestHeaders.substring(idx + 2);
            } else { // it is the last line
                String h = requestHeaders;
                String[] arr = h.split(":");
                headers.put(arr[0], arr[1]);
                break;
            }
        }
        request.setHeaders(headers);
    }

//    private int scanAndMarkEnd(byte[] b, int start, byte... bytesToStop) {
//        for (int i = start, j = 0; i < b.length; i++, j++) {
//            if (j >= bytesToStop.length) {
//                int m = i, n = bytesToStop.length - 1;
//                while (n >= 0) {
//                    if (b[m] != bytesToStop[n]) {
//                        break;
//                    }
//                    m--;
//                    n--;
//                }
//                position.set(i);
//                return m;
//            }
//        }
//        return -1;
//    }

//    private String getStrFromBytes(byte[] b, byte... byteToStop) throws UnsupportedEncodingException {
//        int start = position.get();
//        int end = scanAndMarkEnd(b, start, byteToStop);
//        byte[] bb = new byte[end - start + 1];
//        System.arraycopy(b, start, bb, 0, bb.length);
//        return new String(bb,charset);
//    }
}
