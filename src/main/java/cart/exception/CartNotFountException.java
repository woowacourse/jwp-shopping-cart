package cart.exception;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

public class CartNotFountException extends CartException {

	public static final CartException EXCEPTION = new CartNotFountException();

	public CartNotFountException() {
		super(new ErrorCode(404, "CART-404-1", "장바구니에 해당 상품이 없습니다."));
	}
}
