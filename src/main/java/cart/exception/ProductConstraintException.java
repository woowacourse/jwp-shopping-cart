package cart.exception;

public class ProductConstraintException extends ProductException {
    public ProductConstraintException() {
        super("해당 상품이 장바구니에 존재합니다.");
    }
}
