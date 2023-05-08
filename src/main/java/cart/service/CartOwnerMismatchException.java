package cart.service;

public class CartOwnerMismatchException extends IllegalArgumentException {

    private static final String MESSAGE = "해당 장바구니를 삭제할 권한이 없습니다.";

    public CartOwnerMismatchException() {
        super(MESSAGE);
    }
}
