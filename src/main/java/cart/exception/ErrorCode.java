package cart.exception;

public enum ErrorCode {
    INVALID_CATEGORY("유효하지 않은 카테고리입니다."),
    PRODUCT_NOT_FOUND("상품 정보를 찾을 수 없습니다."),
    INVALID_REQUEST("");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
