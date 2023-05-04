package cart.exception;

public enum ExceptionCode {
    INVALID_PRICE_RANGE("가격은 0원부터 21억원 미만입니다."),
    INVALID_URL_LENGTH("URL은 영문기준 8000자 이하입니다."),
    INVALID_PRODUCT_NAME_LENGTH("상품명은 영문기준 255자 이하입니다."),
    BAD_REQUEST("입력이 잘못되었습니다.");
    
    private String message;
    ExceptionCode(String message) {
        this.message = message;
    }
}
