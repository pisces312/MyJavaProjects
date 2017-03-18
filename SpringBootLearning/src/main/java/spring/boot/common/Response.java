package spring.boot.common;

public class Response {
    private int code = 0;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("{\"code\":%d,\"message\":%s}", code, message);
    }

}
