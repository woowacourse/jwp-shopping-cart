package cart.exception;

public class CartItemDuplicatedException extends RuntimeException {

    public CartItemDuplicatedException() {
        super("장바구니 품목이 중복입니다.");
    }
}
