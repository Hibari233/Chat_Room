package client;

import java.net.Socket;
import java.io.*;
import entity.Request;
import com.alibaba.fastjson2.JSON;

public class MainClient {
    public static void main(String[] args) {
        // Connect Server localhost:8024
        String ip = "localhost";
        int port = 8024;
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Client connected to server: " + ip + ":" + port);
            Request req = new Request();
            req.setAction("test");
            String req_str = JSON.toJSONString(req);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(req_str);
            writer.flush();
            System.out.println("Client sent request: " + req_str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
