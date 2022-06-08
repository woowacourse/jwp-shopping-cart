package woowacourse.shoppingcart.exception.bodyexception;

public class IllegalQuantityException extends IllegalRequestException {

    public IllegalQuantityException() {
        super("1100", "잘못된 형식입니다.");
    }
}
