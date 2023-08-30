package entity;

import java.util.HashMap;
import java.util.Map;
public class Request {
    private String action;
    /*
    定义用户的属性，这里Long代表的为用户id，Object为用户实体属性
     */
    private Map<Long, Object> attributesMap;

    public Request() {
        this.attributesMap = new HashMap<Long, Object>();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<Long, Object> getAttributesMap() {
        return attributesMap;
    }

    public Object getAttribute(Long name) {
        return this.attributesMap.get(name);
    }

    public void setAttribute(Long name, Object value) {
        this.attributesMap.put(name, value);
    }

    public void removeAttribute(Long name) {
        this.attributesMap.remove(name);
    }

    public void clearAttribute() {
        this.attributesMap.clear();
    }
}