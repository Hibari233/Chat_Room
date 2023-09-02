package entity;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson2.*;

public class Response {
    private ResponseStatus status;
    private ResponseType type;
   /*
   同理，详情见Request
    */
    private Map<String, Object> datamap;

    private OutputStream outputStream;

    public Response() {
        this.status = ResponseStatus.OK;
        this.datamap = new HashMap<String, Object>();
    }

    public ResponseType getType() {
        return type;
    }
    public void setType(ResponseType type) {
        this.type = type;
    }
    public ResponseStatus getStatus() { return this.status; }
    public void setStatus(ResponseStatus status) { this.status = status; }
    public Map<String, Object> getDataMap() { return datamap; }
    public void setDataMap(Map<String, Object> datamap) { this.datamap = datamap; }
    public OutputStream getOutputStream() { return outputStream; }
    public void setOutputStream(OutputStream outputStream) { this.outputStream = outputStream; }
    public void setData(String name, Object value) {
        this.datamap.put(name, value);
    }
    public Object getData(String name) { return this.datamap.get(name); }
    public void removeData(String name) {this.datamap.remove(name); }
    public void clearData() { this.datamap.clear(); }
}
