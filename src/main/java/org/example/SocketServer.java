package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.example.config.ProcessorConfiguration;
import org.example.exception.FailedToAcceptConnection;
import org.example.exception.ServerSocketNotCreated;
import org.example.httpRequest.HttpRequest;
import org.example.requestProcessor.ReqProcessor;

public class SocketServer extends Thread {

    private static final ReqProcessor processor = ProcessorConfiguration.getProcessor();

    private final ServerSocket serverSocket;

    public SocketServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new ServerSocketNotCreated(e);
        }
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                processInput(socket);
                socket.close();
            } catch (IOException e) {
                throw new FailedToAcceptConnection(e);
            }
        }
    }

    private void processInput(Socket socket) {
        HttpRequest httpRequest = new HttpRequest(socket);
        processor.process(httpRequest);
    }
}
