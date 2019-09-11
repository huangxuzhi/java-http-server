package http.server.request;


import http.server.common.Student;
import http.server.common.ThreadOutputStream;
import http.server.request.body.JsonRequestBody;
import http.server.request.handler.MyHttpRequestHandler;
import http.server.response.HttpResponse;
import http.server.response.Response;
import http.server.factory.HttpResponseFactory;
import http.server.parser.HttpRequestParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Connection implements Runnable {

    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            int available = is.available();
            if (available > 0) {
//                byte[] b = new byte[available];
//                is.read(b);
//                String s = new String(b,"UTF-8");
//                System.out.println(s);
//                ThreadOutputStream.OUTPUT_STREAM.set(socket.getOutputStream());
//                HttpResponse response = HttpResponseFactory.textResponse();
//                response.write();
                ThreadOutputStream.OUTPUT_STREAM.set(socket.getOutputStream());
                HttpRequestParser p = new HttpRequestParser(is);
                HttpRequest q = p.parse();
                MyHttpRequestHandler h = new MyHttpRequestHandler();
                HttpResponse r = h.handle(q);
                r.setBody("Hello World!");
                r.write();
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            Response response = null;
            try {
                response = HttpResponseFactory.baseResponse();
                response.write();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
}
