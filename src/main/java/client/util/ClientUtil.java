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
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(DataBuffer.socket.getOutputStream()));
            writer.write(JSON.toJSONString(request) + "\n");
            writer.flush();
            System.out.println("客户端发送了请求对象:" + request.getAction()  + request.getAttributeCustom("user"));
        } catch (IOException e) {
            throw e;
        }

        if (!"exit".equals(request.getAction())) {
            // 获取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(DataBuffer.socket.getInputStream()));
            String rep = reader.readLine();
            response = JSON.parseObject(rep, Response.class);
            System.out.println("客户端获取到了响应对象:" + response.getStatus());
        }else {
            System.out.println("客户端断开连接了");
        }
        return response;
    }
}
