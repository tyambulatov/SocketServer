package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.example.exception.FailedToAcceptConnection;
import org.example.exception.ServerSocketNotCreated;
import org.example.httpRequest.HttpRequest;
import org.example.requestProcessor.CompositeRequestProcessor;
import org.example.requestProcessor.ExceptionHandlingProcessor;
import org.example.requestProcessor.PathRegexRule;
import org.example.requestProcessor.PathStartsRule;
import org.example.requestProcessor.ReqProcessor;
import org.example.requestProcessor.user.SingleUserRequestProcessor;
import org.example.requestProcessor.user.UsersRequestProcessor;

public class SocketServer extends Thread {

    ServerSocket serverSocket;

    ReqProcessor processor = new ExceptionHandlingProcessor(
            new CompositeRequestProcessor(
                    Map.of(
                            new PathStartsRule("/users"), new CompositeRequestProcessor(
                                    Map.of(
                                            new PathRegexRule("/users/\\d"), new SingleUserRequestProcessor(),
                                            new PathStartsRule("/users"), new UsersRequestProcessor()
                                    )
                            ),
                            new PathStartsRule("/products"), new CompositeRequestProcessor(Map.of())
                    )
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
