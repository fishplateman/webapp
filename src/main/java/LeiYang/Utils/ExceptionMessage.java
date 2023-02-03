package LeiYang.Utils;

public class ExceptionMessage<T> {
    private int code;
    private String message;
    public ExceptionMessage(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
