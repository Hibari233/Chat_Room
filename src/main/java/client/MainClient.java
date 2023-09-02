package client;

import java.net.Socket;
import java.io.*;

import client.ui.LoginFrame;
import client.ui.RegisterFrame;
import entity.Request;
import com.alibaba.fastjson2.JSON;
import entity.ResponseType;

import javax.xml.crypto.Data;

public class MainClient {
    public static void main(String[] args) {
        // Connect Server localhost:8024
        String ip = "localhost";
        int port = 8024;
        try {
            Socket socket = new Socket(ip, port);
            DataBuffer.socket = socket;
        } catch (Exception e) {
            e.printStackTrace();
        }
        new RegisterFrame();
    }
}
