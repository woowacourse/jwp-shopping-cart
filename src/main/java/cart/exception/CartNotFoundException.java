package cart.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("존재하지 않는 장바구니의 ID 입니다.");
    }
}
