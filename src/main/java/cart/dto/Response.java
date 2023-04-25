package cart.dto;

public abstract class Response {
    private final String code;
    private final String message;

    protected Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
