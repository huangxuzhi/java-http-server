package http.server;

import http.server.common.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpServer {

    private Executor executor;

    private int port;

    public HttpServer(int port, int threads) {
        this.executor = Executors.newFixedThreadPool(threads);
        this.port = port;
    }

    public void start() throws IOException{
        ServerSocket serverSocket = new ServerSocket(8080);
        while(true) {
            Socket socket = serverSocket.accept();
            executor.execute(new Connection(socket));
        }
    }
}
