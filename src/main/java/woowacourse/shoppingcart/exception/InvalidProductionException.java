package woowacourse.shoppingcart.exception;

public class InvalidProductionException extends RuntimeException {

    public InvalidProductionException() {
        this("존재하지 않는 상품입니다.");
    }

    public InvalidProductionException(final String msg) {
        super(msg);
    }
}
