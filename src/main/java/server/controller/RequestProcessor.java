package server.controller;

import java.net.Socket;

public class RequestProcessor implements Runnable {
    private Socket socket;
    public RequestProcessor(Socket socket) {
        this.socket = socket;
    }

    public void run() {

    }

}
