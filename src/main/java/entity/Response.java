package entity;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private ResponseStatus status;
    /*
    定义用户的属性，这里Long代表的为用户id，Object为用户实体属性
     */
    private Map<Long, Object> datamap;

    private OutputStream outputStream;

    public Response() {
        this.status = ResponseStatus.OK;
        this.datamap = new HashMap<Long, Object>();
    }

    public ResponseStatus getStatus() { return status; }
    public void setStatus(ResponseStatus status) { this.status = status; }
    public Map<Long, Object> getDataMap() { return datamap; }
    public void setDataMap(Map<Long, Object> datamap) { this.datamap = datamap; }
    public OutputStream getOutputStream() { return outputStream; }
    public void setOutputStream(OutputStream outputStream) { this.outputStream = outputStream; }
    public void setData(Long name, Object value) {this.datamap.put(name, value); }
    public void getData(Long name) { this.datamap.get(name); }
    public void removeData(Long name) {this.datamap.remove(name); }
    public void clearData() { this.datamap.clear(); }
}
