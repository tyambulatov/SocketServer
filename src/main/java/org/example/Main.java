package org.example;

public class Main {
    public static void main(String[] args) {
        int port = 8080;
        new Thread(new SocketServer(port)).start();
    }
}
