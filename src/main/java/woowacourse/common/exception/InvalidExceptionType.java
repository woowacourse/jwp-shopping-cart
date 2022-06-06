package woowacourse.common.exception;

public enum InvalidExceptionType {

    USERNAME_FORMAT("아이디는 최소 4글자, 최대 20글자여야 합니다."),
    PASSWORD_FORMAT("비밀번호는 알파벳, 숫자, 특수문자로 구성되어야 합니다. (8~20글자)"),
    WRONG_PASSWORD("현재 비밀번호를 잘못 입력하였습니다."),
    NICKNAME_FORMAT("닉네임은 최소 1글자, 최대 10글자여야 합니다."),
    AGE_FORMAT("나이는 0~200세만 입력할 수 있습니다."),
    PRODUCT("올바르지 않은 사용자 이름이거나 상품 아이디 입니다."),
    ORDER("유효하지 않은 주문입니다."),
    NOT_IN_CART("장바구니 아이템이 없습니다."),
    NOT_FOUND_IN_CART("유효하지 않은 장바구니입니다."),
    ;

    private final String message;

    InvalidExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
