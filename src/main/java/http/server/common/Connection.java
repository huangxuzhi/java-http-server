package http.server.common;


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
        byte[] buffer = new byte[1024 * 1024 * 10];
        try {
            InputStream is = socket.getInputStream();
            int bytesToRead = is.read(buffer);
            String s = new String(buffer, "UTF-8");
            Request request = HttpRequestParser.getInstance().parse(s);
            Response response = DefaultHttpResponseFactory.createResponse(socket.getOutputStream());
            response.write();
            System.out.println("...");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
