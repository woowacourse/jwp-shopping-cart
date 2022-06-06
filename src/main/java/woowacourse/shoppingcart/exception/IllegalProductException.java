package woowacourse.shoppingcart.exception;

public class IllegalProductException extends IllegalRequestException {

    public IllegalProductException() {
        super("", "물품이 존재하지 않습니다.");
    }
}
