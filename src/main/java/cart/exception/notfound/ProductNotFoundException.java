package cart.exception.notfound;

public class ProductNotFoundException extends NotFoundException {

    public ProductNotFoundException() {
        super("존재하지 않는 상품입니다.");
    }
}
