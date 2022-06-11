package woowacourse.shoppingcart.exception.notfound;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
