package woowacourse.shoppingcart.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        this("존재하지 않는 상품 ID입니다.");
    }

    public ProductNotFoundException(final String msg) {
        super(msg);
    }
}
