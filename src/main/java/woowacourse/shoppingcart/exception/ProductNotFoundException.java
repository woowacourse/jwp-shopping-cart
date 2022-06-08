package woowacourse.shoppingcart.exception;

public class ProductNotFoundException extends ProductException {

    public ProductNotFoundException() {
        super("존재하지 않는 상품입니다.");
    }
}
