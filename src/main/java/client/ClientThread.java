package client;

import client.ui.ChatFrame;
import client.util.ClientUtil;
import com.alibaba.fastjson2.JSON;
import entity.Message;
import entity.Response;
import entity.ResponseType;
import entity.User;

import java.io.*;

import static client.DataBuffer.socket;

public class ClientThread extends Thread{
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(DataBuffer.socket.getInputStream()));
            while (socket.isConnected()) {
//                Response response = (Response) DataBuffer.ois.readObject();
//                ResponseType type = response.getType();
                String rep = reader.readLine();
                Response response = JSON.parseObject(rep, Response.class);
                ResponseType type = response.getType();
                System.out.println("获取了响应内容：" + type);
                if (type == ResponseType.LOGIN) {
                    User newUser = (User)response.getDataCustom("loginUser");
                    DataBuffer.onlineUserListModel.addElement(newUser);

                    ChatFrame.onlineCountLbl.setText(
                            "在线用户列表("+ DataBuffer.onlineUserListModel.getSize() +")");
                    ClientUtil.appendTxt2MsgListArea("【系统消息】用户"+newUser.getNickName() + "上线了！\n");
                }else if(type == ResponseType.LOGOUT){
                    User newUser = (User)response.getDataCustom("logoutUser");
                    DataBuffer.onlineUserListModel.removeElement(newUser);

                    ChatFrame.onlineCountLbl.setText(
                            "在线用户列表("+ DataBuffer.onlineUserListModel.getSize() +")");
                    ClientUtil.appendTxt2MsgListArea("【系统消息】用户"+newUser.getNickName() + "下线了！\n");

                }else if(type == ResponseType.CHAT){ //聊天
                    Message msg = (Message)response.getDataCustom("txtMsg");
                    ClientUtil.appendTxt2MsgListArea(msg.getMessage());
                }//else if(type == ResponseType.SHAKE){ //振动
//                    Message msg = (Message)response.getDataCustom("ShakeMsg");
//                    ClientUtil.appendTxt2MsgListArea(msg.getMessage());
//                    new JFrameShaker(this.currentFrame).startShake();
//                }else if(type == ResponseType.TOSENDFILE){ //准备发送文件
//                    toSendFile(response);
//                }else if(type == ResponseType.AGREERECEIVEFILE){ //对方同意接收文件
//                    sendFile(response);
//                }else if(type == ResponseType.REFUSERECEIVEFILE){ //对方拒绝接收文件
//                    ClientUtil.appendTxt2MsgListArea("【文件消息】对方拒绝接收，文件发送失败！\n");
//                }else if(type == ResponseType.RECEIVEFILE){ //开始接收文件
//                    receiveFile(response);
//                }else if(type == ResponseType.BOARD){
//                    Message msg = (Message)response.getData("txtMsg");
//                    ClientUtil.appendTxt2MsgListArea(msg.getMessage());
//                }else if(type == ResponseType.REMOVE){
//                    ChatFrame.remove();
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
