package client;

import java.net.Socket;

public class MainClient {
    public static void main(String[] args) {
        // Connect Server localhost:8024
        String ip = "localhost";
        int port = 8024;
        try {
            Socket socket =  new Socket(ip, port);;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
