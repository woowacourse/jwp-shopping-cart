package woowacourse.shoppingcart.exception.format;

public class InvalidQuantityException extends FormatException{
    public InvalidQuantityException() {
        super("잘못된 수량 정보입니다.");
    }
}
