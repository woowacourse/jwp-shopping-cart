package woowacourse.shoppingcart.exception;

public class InvalidProductPriceException extends ProductException {

    public InvalidProductPriceException() {
        super("상품 가격은 양의 정수가 되어야 합니다.");
    }
}
