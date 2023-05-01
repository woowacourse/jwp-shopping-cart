package cart.web.exception;

public enum ErrorCode {
    INVALID_CATEGORY("유효하지 않은 카테고리입니다."),
    PRODUCT_NOT_FOUND("상품 정보를 찾을 수 없습니다."),
    INVALID_DELETE("삭제할 수 없습니다. 관리자에게 문의하세요."),
    INVALID_REQUEST(""),
    UNEXPECTED_EXCEPTION("예상치 못한 예외가 발생했습니다. 잠시만 기다려주세요.");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
