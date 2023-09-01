package entity;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson2.*;

public class Response {
    private ResponseStatus status;
   /*
   同理，详情见Request
    */
    private Map<String, String> datamap;

    private OutputStream outputStream;

    public Response() {
        this.status = ResponseStatus.OK;
        this.datamap = new HashMap<String, String>();
    }

    public ResponseStatus getStatus() { return status; }
    public void setStatus(ResponseStatus status) { this.status = status; }
    public Map<String, String> getDataMap() { return datamap; }
    public void setDataMap(Map<String, String> datamap) { this.datamap = datamap; }
    public OutputStream getOutputStream() { return outputStream; }
    public void setOutputStream(OutputStream outputStream) { this.outputStream = outputStream; }
    public void setData(String name, Object object) {
        String value = JSON.toJSONString(object);
        this.datamap.put(name, value);
    }
    public void getData(String name) { this.datamap.get(name); }
    public void removeData(String name) {this.datamap.remove(name); }
    public void clearData() { this.datamap.clear(); }
}
