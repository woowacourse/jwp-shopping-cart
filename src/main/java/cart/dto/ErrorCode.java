package cart.dto;

public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "잘못된 입력값입니다. 다시 입력해주세요."),
    INVALID_DATABASE_ACCESS(400, "데이터 베이스 접근 에러");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
