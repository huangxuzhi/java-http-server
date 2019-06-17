package http.server.request;


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
//                int bytesToRead = is.read(b);
//                String s = new String(b, "UTF-8");
//                System.out.println(s);
                Response response = DefaultHttpResponseFactory.createResponse(socket.getOutputStream());
                response.write();
            }
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
