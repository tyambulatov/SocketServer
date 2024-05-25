package org.example;

import org.example.simpleClient.SimpleClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SocketServerTest {
    int port = 8080;
    SocketServer socketServer;
    SimpleClient simpleClient;
    String request = "GET / HTTP/1.1\r\nHost: localhost:8080\r\nAccept: */*\r\n\r\n";

    @Test
    void test() throws IOException {
        socketServer = new SocketServer(port);
        simpleClient = new SimpleClient("localhost", 8080, request);
        new Thread(simpleClient).start();
        new Thread(socketServer).start();
    }

    @AfterEach
    void tearDown() {
    }
}