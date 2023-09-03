package server.controller;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import entity.*;
import server.DataBuffer;
import server.OnlineClientIOcache;
import entity.Request;
import server.controller.UserController;
import util.DatabaseUtil;
import util.PasswordEncryption;

import javax.xml.crypto.Data;

public class RequestProcessor implements Runnable {
    private Socket socket;
    public RequestProcessor(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        boolean quit_flag = false;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(!quit_flag) {
                System.out.println("输入流："+reader);
                String request = reader.readLine();
                System.out.println("服务器接受到消息："+request);
                Request req = JSON.parseObject(request, Request.class);
                String action = req.getAction();
                System.out.println("Server received Request action: " + action);

                if(action.equals("userRegister")){
                    register(reader, writer, req);
                }
                else if(action.equals("userLogin")){
                    login(reader, writer, req);
                }
                else if(action.equals("exit")){
                    quit_flag = logout(reader, writer, req);
                }
                else if(action.equals("chat")){
                    chat(req);
                }
                else if(action.equals("shake")){

                }
                else{

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // userRegister

    public void register(BufferedReader inputStream, BufferedWriter outputStream, Request request) throws IOException, NoSuchAlgorithmException {
        // process
        JSONObject data = (JSONObject) request.getAttributeCustom("user");
        User user = new User(data.getString("nickName"), data.getString("password"), data.getString("sex"));

        UserController userController = new UserController();
        userController.addUser(user);
        List<User> users = userController.loadAllUser();
        user.setId(users.size());
        System.out.println(user.getId());

        // response
        Response response = new Response();
        response.setStatus(ResponseStatus.OK);
        response.setDataCustom("user", user);
        outputStream.write(JSON.toJSONString(response) + "\n");
        outputStream.flush();

        // add user into registed user list
        // TODO: 把用户加入用户列表中
    }

    public void login(BufferedReader inputStream, BufferedWriter outputStream, Request request) throws IOException, NoSuchAlgorithmException {
        // process basic information
        long id = Long.parseLong((String) request.getAttributeCustom("id"));
        String password = (String) request.getAttributeCustom("password");

        // use UserService to login
        UserController userController = new UserController();
        User user = userController.login(id, password);

        // response

        Response response = new Response();

        // check user whether login successfully
        if(user != null) {
            // grab user from online user list
            if (DataBuffer.onlineUsersMap.containsKey(user.getId())) {
                // user has already logged in

                // response
                response.setStatus(ResponseStatus.OK);
                response.setDataCustom("message", "user has already logged in");
                outputStream.write(JSON.toJSONString(response) + '\n');
                outputStream.flush();
            }
            else {
                // user login
                // add user into online user list
                DataBuffer.onlineUsersMap.put(user.getId(), user);

                // add online user list into the response
                response.setDataCustom("onlineUsersMap", new CopyOnWriteArrayList<User>(DataBuffer.onlineUsersMap.values()));

                // response
                response.setStatus(ResponseStatus.OK);
                response.setDataCustom("user", user);
                outputStream.write(JSON.toJSONString(response) + '\n');
                outputStream.flush();

                // response to other client (notify other client user login)
                Response response_to_all = new Response();
                response_to_all.setType(ResponseType.LOGIN);
                response_to_all.setDataCustom("loginUser", user);
                sendToAll(response_to_all);

                // put user into online user io cache
                DataBuffer.onlineUserIOCacheMap.put(user.getId(), new OnlineClientIOcache(inputStream, outputStream));

                // put user into online user table model
                String[] UserRow = {String.valueOf(user.getId()), user.getNickName(), user.getSex()};
                DataBuffer.onlineUserTableModel.addRow(UserRow);
            }
        }
        else {
            // login failed
            response.setStatus(ResponseStatus.OK);
            response.setDataCustom("msg", "账号或密码不正确！");
            // response
            outputStream.write(JSON.toJSONString(response) + '\n');
            outputStream.flush();
        }

    }

    // client exit
    public boolean logout(BufferedReader inputStream, BufferedWriter outputStream, Request request) throws IOException, NoSuchAlgorithmException {
        // print leave message on server side
        JSONObject data = (JSONObject) request.getAttributeCustom("user");
        User user = new User(data.getString("nickName"), data.getString("password"), data.getString("sex"));
        UserController userController = new UserController();
        List<User> users = userController.loadAllUser();
        user.setId(users.size());
        System.out.println("User " + user.getNickName() + " has left the chat room.");

        // remove user from online user list
        DataBuffer.onlineUsersMap.remove(user.getId());
        // remove user from online user IO cache Map
        DataBuffer.onlineUserIOCacheMap.remove(user.getId());

        // response
        Response response = new Response();
        response.setStatus(ResponseStatus.OK);
        response.setDataCustom("logoutUser", user);
        outputStream.write(JSON.toJSONString(response) + '\n');
        outputStream.flush();

        // remove user from online user table model
        DataBuffer.onlineUserTableModel.remove(user.getId());
        sendToAll(response);

        return true;
    }

    // group chat and private chat
    public void chat(Request request) throws IOException {
        Message msg = (Message) request.getAttributeCustom("msg");
        // response
        Response response = new Response();
        response.setStatus(ResponseStatus.OK);
        response.setType(ResponseType.CHAT);
        response.setDataCustom("txtMsg", msg);

        // getToUser == null -> groupChat
        // getToUser != null -> privateChat
        if(msg.getToUser() != null) {
            OnlineClientIOcache toUserIO = DataBuffer.onlineUserIOCacheMap.get(msg.getToUser().getId());
            sendResponse(toUserIO, response);
        }
        else {
            for (Long id : DataBuffer.onlineUserIOCacheMap.keySet()) {
                if (msg.getFromUser().getId() == id) continue;
                sendResponse(DataBuffer.onlineUserIOCacheMap.get(id), response);

            }
        }

    }

    private void sendResponse(OnlineClientIOcache onlineUserIO, Response response) throws IOException {
        BufferedWriter writer = onlineUserIO.getOutputStream();
        writer.write(JSON.toJSONString(response) + "\n");
        writer.flush();
    }


    // send to all online clients
    private void sendToAll(Response response) throws IOException {
        for(OnlineClientIOcache onlineUserIO : DataBuffer.onlineUserIOCacheMap.values()) {
            BufferedWriter writer = onlineUserIO.getOutputStream();
            writer.write(JSON.toJSONString(response) + "\n");
            writer.flush();
        }
    }
}
