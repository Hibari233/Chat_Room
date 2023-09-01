package util;

import entity.Request;
import com.alibaba.fastjson2.*;
import entity.Response;

public class ObjectToJson {
    public String RequestToJson(Request request) {
        return JSON.toJSONString(request);
    }
    public String ResponseToJson(Response response) {
        return  JSON.toJSONString(response);
    }
}
