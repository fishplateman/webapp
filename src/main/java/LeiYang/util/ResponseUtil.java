package LeiYang.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static Object ok() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", ResponseCode.OK.getCode());
        obj.put("errmsg", ResponseCode.OK.getMessage());
        return obj;
    }

    public static Object ok(Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", ResponseCode.OK.getCode());
        obj.put("errmsg", ResponseCode.OK.getMessage());
        obj.put("data", data);
        return obj;
    }

    public static Object fail(ResponseCode code) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", code.getCode());
        obj.put("errmsg", code.getMessage());
        return obj;
    }

    public static Object fail(ResponseCode code, String errmsg) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", code.getCode());
        obj.put("errmsg", errmsg);
        return obj;
    }


}
