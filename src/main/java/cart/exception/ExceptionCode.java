package cart.exception;

public enum ExceptionCode {
    INVALID_PRICE_RANGE("가격은 0원부터 21억원 미만입니다."),
    INVALID_URL_LENGTH("URL은 영문기준 8000자 이하입니다."),
    INVALID_PRODUCT_NAME_LENGTH("상품명은 영문기준 255자 이하입니다."),
    BAD_REQUEST("입력이 잘못되었습니다."),
    NO_AUTHORIZATION_HEADER("인증 헤더가 없습니다."),
    AUTHORIZATION_FAIL("잘못된 사용자입니다."),
    EMPTY_REQUEST("요청이 없거나 공백입니다."),
    NO_DATA("해당하는 데이터가 없습니다."),
    ILLEGAL_ARGUMENT("부적절한 입력입니다."),
    INVALID_ID("ID가 양의 정수가 아닙니다."),
    INVALID_EMAIL("이메일이 올바르지 않습니다."),
    INVALID_PASSWORD("비밀번호가 올바르지 않습니다.");

    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }
}
