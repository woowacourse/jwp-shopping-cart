package cart.service;

public class CartNotFoundException extends IllegalArgumentException {

    private static final String MESSAGE = "존재하지 않는 장바구니입니다.";

    public CartNotFoundException() {
        super(MESSAGE);
    }
}
