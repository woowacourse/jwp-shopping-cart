package woowacourse.shoppingcart.exception.format;

public class InvalidPriceException extends FormatException{
    public InvalidPriceException() {
        super("잘못된 가격 정보입니다.");
    }
}
