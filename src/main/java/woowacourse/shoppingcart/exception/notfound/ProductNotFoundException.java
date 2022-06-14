package woowacourse.shoppingcart.exception.notfound;

public class ProductNotFoundException extends NotFoundException {

    public static final String MESSAGE = "상품을 찾을 수 없습니다.";

    public ProductNotFoundException() {
        this(MESSAGE);
    }

    public ProductNotFoundException(final String message) {
        super(message);
    }
}
