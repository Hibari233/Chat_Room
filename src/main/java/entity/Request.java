package entity;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String action;
    private ResponseType type;
    /*
    定义用户的属性，这里String代表的为发送的格式，Object为发送的类
    使用String类型，将Object序列化为String类型
     */
    private Map<String, Object> attributesMap;

    public Request() {
        this.attributesMap = new HashMap<String, Object>();
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {this.action = action;}

    public Map<String, Object> getAttributeMap() {
        return attributesMap;
    }
    public Object getAttributeCustom(String name) {

        return this.attributesMap.get(name);
    }

    public void setAttributeCustom(String name, Object value) {
        this.attributesMap.put(name, value);
    }

    public void removeAttribute(String name) {
        this.attributesMap.remove(name);
    }

    public void clearAttribute() {
        this.attributesMap.clear();
    }
}
