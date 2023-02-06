package LeiYang.util;

public enum ResponseCode {
    OK(0,"成功"),
    USER_ERROR(400,"用户错误"),
    INTERNAL_SERVER_ERR(500,"服务器内部错误");

    private int code;
    private String message;
    ResponseCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage(){
        return message;
    }
}
