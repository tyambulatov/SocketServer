package org.example.simpleClient;

import java.io.*;
import java.net.Socket;

public class SimpleClient extends Thread {
    private final String domain;
    private final int port;
    private String httpRequest;

    public SimpleClient(String domain, int port, String httpRequest) {
        this.domain = domain;
        this.port = port;
        this.httpRequest = httpRequest;
    }

    public void run() {
//        while (true) {
            try {
                Socket socket = new Socket(domain, port);
                sendRequest(socket, httpRequest);
//                System.out.println(HttpRequest.parseHttpRequestString(socket));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//        }
    }

    private void sendRequest(Socket socket, String httpRequest) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write(httpRequest);
        bufferedWriter.flush();
    }
}
