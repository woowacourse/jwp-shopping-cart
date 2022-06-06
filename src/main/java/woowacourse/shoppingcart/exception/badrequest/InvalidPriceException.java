package woowacourse.shoppingcart.exception.badrequest;

public class InvalidPriceException extends BadRequestException {
    public InvalidPriceException() {
        this("유효하지 않은 가격입니다.");
    }

    public InvalidPriceException(final String msg) {
        super(0, msg);
    }
}
