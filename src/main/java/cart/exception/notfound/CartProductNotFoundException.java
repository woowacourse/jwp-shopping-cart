package cart.exception.notfound;

public class CartProductNotFoundException extends NotFoundException {

    public CartProductNotFoundException() {
        super("장바구니에 존재하지 않는 상품입니다.");
    }
}
