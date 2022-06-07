package woowacourse.shoppingcart.exception;

public class ProductNotFoundException extends DataNotFoundException {

    private static final String MESSAGE = "존재하지 않는 상품입니다.";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
