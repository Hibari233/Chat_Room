package client;

import client.entity.OnlineUserListModel;
import entity.User;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.List;

public class DataBuffer {
    // user information of client
    public static User currentUser;
    // onlineUserTable list
    public static List<User> onlineUsers;
    // socket from client to server
    public static Socket socket;
    // 服务器配置参数属性集
    public static Properties configProp;
    // 当前客户端的屏幕尺寸
    public static Dimension screenSize;
    // ip
    public static String ip ;
    // 用于接收文件端口
    public static final int RECEIVE_FILE_PORT = 8025;
    //在线用户List model
    public static OnlineUserListModel onlineUserListModel;

    static{
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //加载服务器配置文件
        configProp = new Properties();
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            configProp.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("serverconfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
