package cart.exception.product;

public class PriceNotUnderZeroException extends ProductException {

    private static final String PRICE_NEGATIVE_EXCEPTION = "상품 가격은 음수가 될 수 없습니다.";

    public PriceNotUnderZeroException() {
        super(PRICE_NEGATIVE_EXCEPTION);
    }
}
