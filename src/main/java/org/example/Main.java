package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        int port = 8080;
        new Thread(new SocketServer(port)).start();
    }
}
