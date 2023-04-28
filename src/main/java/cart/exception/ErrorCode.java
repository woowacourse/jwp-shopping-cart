package cart.exception;

public enum ErrorCode {
    PRODUCT_NOT_FOUND("상품 정보를 찾을 수 없습니다."),
    PRODUCT_INVALID_UPDATE("상품 정보 수정 중 예기치 못한 오류가 발생하였습니다."),
    PRODUCT_INVALID_DELETE("상품 정보 삭제 중 예기치 못한 오류가 발생하였습니다."),
    INVALID_REQUEST("");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
