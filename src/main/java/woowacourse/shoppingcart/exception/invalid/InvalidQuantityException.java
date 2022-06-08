package woowacourse.shoppingcart.exception.invalid;

public class InvalidQuantityException extends InvalidException {

    public InvalidQuantityException() {
        super("제품 수량은 1에서 99사이의 정수만 가능합니다😅");
    }
}
