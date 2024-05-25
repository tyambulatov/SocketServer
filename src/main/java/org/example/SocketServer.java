package org.example;

import org.example.exception.FailedToAcceptConnection;
import org.example.exception.ServerSocketNotCreated;
import org.example.requestProcessor.CompositeRequestProcessor;
import org.example.httpRequest.HttpRequest;
import org.example.requestProcessor.PathRequestRule;
import org.example.requestProcessor.user.SingleUserRequestProcessor;
import org.example.requestProcessor.user.UsersRequestProcessor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class SocketServer extends Thread {

    ServerSocket serverSocket;

    CompositeRequestProcessor processor = new CompositeRequestProcessor(
            Map.of(
                    new PathRequestRule("/users"), new CompositeRequestProcessor(
                            Map.of(
                                    new PathRequestRule("/users/\\d"), new SingleUserRequestProcessor(),
                                    new PathRequestRule("/users"), new UsersRequestProcessor()
                            )
                    ),
                    new PathRequestRule("/products"), new CompositeRequestProcessor(Map.of())
            )
    );

//    users/123/cart/1

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
