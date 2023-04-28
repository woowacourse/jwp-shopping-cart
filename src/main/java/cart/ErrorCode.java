package cart;

public enum ErrorCode {

    NOT_VALID_ID(400, "유효하지 않은 id 입니다."),
    NOT_VALID_PRICE(400,"유효한 가격이 아닙니다. 가격은 0이상의 정수이어야 합니다.");

    private final int  statusCode;
    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
