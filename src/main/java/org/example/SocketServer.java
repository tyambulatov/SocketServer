package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.example.config.ServerConfiguration;
import org.example.httpRequest.HttpRequest;
import org.example.requestProcessor.ReqProcessor;

public class SocketServer extends Thread {

    private static final ReqProcessor processor = ServerConfiguration.getProcessor();

    private final ServerSocket serverSocket;

    public SocketServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Error creating serverSocket", e);
        }
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                processInput(socket);
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException("Error working with socket", e);
            }
        }
    }

    private void processInput(Socket socket) {
        HttpRequest httpRequest = new HttpRequest(socket);
        processor.process(httpRequest);
    }
}
