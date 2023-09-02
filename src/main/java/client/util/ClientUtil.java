package client.util;

import client.DataBuffer;
import com.alibaba.fastjson2.JSON;
import entity.Request;
import entity.Response;

import java.io.*;
public class ClientUtil {
    // 发送请求对象，主动接收响应
    public static Response sendTextRequest(Request request) throws IOException {
        Response response = null;
        try {
            DataBuffer.outputStream.write(JSON.toJSONString(request));
            DataBuffer.outputStream.flush();
            System.out.println("客户端发送了请求对象:" + request.getAction());

            if (!"exit".equals(request.getAction())) {
                // 获取响应
                String rep = String.valueOf(DataBuffer.inputStream.read());
                response = JSON.parseObject(rep, Response.class);
                System.out.println("客户端获取到了响应对象:" + response.getStatus());
            }else {
                System.out.println("客户端断开连接了");
            }
        } catch (IOException e) {
            throw e;
        }
        return response;
    }

    // 发送请求对象，不主动接受响应
    public static void sendTextRequest2(Request request) throws IOException {
        try {
            DataBuffer.outputStream.write(JSON.toJSONString(request));
            DataBuffer.outputStream.flush();
            System.out.println("客户端发送了请求对象:" + request.getAction());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
