package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.controller.RequestProcessor;


public class MainServer {
    public static void main(String[] args) {
        int port = 8024;
        //初始化服务器Socket
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final ServerSocket finalServerSocket = serverSocket;
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        //监听客户端的连接
                        Socket socket = finalServerSocket.accept();
                        System.out.println("客户端已连接，IP："
                                + socket.getInetAddress().getHostAddress()
                                + ":" + socket.getPort());

                        //针对每个客户端启动一个线程，在线程中调用请求处理器来处理每个客户端的请求
                         new Thread(new RequestProcessor(socket)).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
