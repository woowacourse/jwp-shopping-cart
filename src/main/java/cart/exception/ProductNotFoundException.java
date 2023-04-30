package cart.exception;

public class ProductNotFoundException extends ProductException {
    public ProductNotFoundException() {
        super("존재하지 않는 상품의 ID 입니다.");
    }
}
