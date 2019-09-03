package http.server.request;


import http.server.common.Student;
import http.server.exception.ContentTypeNotSupportedException;
import http.server.parser.RequestParser;
import http.server.request.body.JsonRequestBody;
import http.server.response.Response;
import http.server.factory.DefaultHttpResponseFactory;
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
//                String s = new String(b);
//                System.out.println(s);
                HttpRequestParser p = new HttpRequestParser(is);
                HttpRequest q = p.parse();
                ((JsonRequestBody)q.getBody()).getContent(Student.class);
                Response response = DefaultHttpResponseFactory.createResponse(socket.getOutputStream());
                response.write();
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
