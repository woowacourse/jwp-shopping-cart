package woowacourse.shoppingcart.exception;

public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500),
    INVALID_TOKEN_REQUEST(3000),

    INVALID_REQUEST_FORM(1000),
    DUPLICATE_EMAIL(1001),
    INVALID_LOGIN_REQUEST(1002),

    INVALID_CART_REQUEST_FORM(1100),
    DUPLICATE_CART_ITEM(1101),
    NOT_EXIST_ITEM_IN_CART(1102),

    NOT_FOUND_CART_ITEM(1200),

    GENERAL_NOT_FOUND(2000),
    ;

    private final int value;

    ErrorCode(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
