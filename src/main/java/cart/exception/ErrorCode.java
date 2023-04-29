package cart.exception;

public enum ErrorCode {
    PRODUCT_NOT_FOUND("상품 정보를 찾을 수 없습니다."),
    PRODUCT_INVALID_UPDATE("상품 정보 수정 중 예기치 못한 오류가 발생하였습니다."),
    PRODUCT_INVALID_DELETE("상품 정보 삭제 중 예기치 못한 오류가 발생하였습니다."),
    INVALID_REQUEST(""),
    PRODUCT_INVALID_CATEGORY("유효하지 않은 카테고리 정보입니다."),
    PRODUCT_NAME_LENGTH("상품 이름의 길이는 1 ~ 25글자여야 합니다."),
    PRODUCT_PRICE_RANGE("상품 가격은 0 ~ 10,000,000원까지 가능합니다."),
    INTERNAL_SERVER_ERROR("서버에서 예기치 못한 오류가 발생하였습니다.");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
