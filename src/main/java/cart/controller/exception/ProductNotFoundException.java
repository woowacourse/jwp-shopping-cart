package cart.controller.exception;

public class ProductNotFoundException extends IllegalArgumentException {

    public ProductNotFoundException() {
        super("존재하지 않는 상품입니다.");
    }
}
