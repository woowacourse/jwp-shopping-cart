package cart.controller.Exception;

public class CartHasDuplicatedItemsException extends RuntimeException {
    public CartHasDuplicatedItemsException() {
        super("[ERROR] 장바구니에는 동일 상품을 중복으로 담을 수 없습니다.");
    }
}
