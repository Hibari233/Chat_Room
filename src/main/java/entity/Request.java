package entity;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson2.*;

public class Request {
    private String action;
    /*
    定义用户的属性，这里String代表的为发送的格式，Object为发送的类
    使用String类型，将Object序列化为String类型
     */
    private Map<String, Object> attributesMap;

    public Request() {
        this.attributesMap = new HashMap<String, Object>();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {this.action = action;}

    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    public Object getAttribute(String name) {
        return this.attributesMap.get(name);
    }

    public void setAttribute(String name, Object value) {
        this.attributesMap.put(name, value);
    }

    public void removeAttribute(String name) {
        this.attributesMap.remove(name);
    }

    public void clearAttribute() {
        this.attributesMap.clear();
    }
}
