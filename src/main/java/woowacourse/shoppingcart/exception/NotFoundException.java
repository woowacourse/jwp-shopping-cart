package woowacourse.shoppingcart.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        this("존재하지 않는 상품입니다.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
