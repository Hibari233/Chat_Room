package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.Executor;

import entity.User;
import server.controller.RequestProcessor;

public class MainServer {
    /* 定义一个线程池用于管理客户端线程 */
    static Executor pool;
    //初始化在线用户
    static Map<User, PrintWriter> online;
    public static void main(String[] args) throws IOException {
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
                        //加入线程池，分配线程
                        RequestProcessor clientHandler = new RequestProcessor(socket);
                        pool.execute(clientHandler);

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
