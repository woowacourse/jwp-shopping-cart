package cart.exception;

import org.springframework.dao.DataAccessException;

public class CartItemNotFoundException extends DataAccessException {

    private final static String MESSAGE = "존재하지 않는 장바구니 아이템입니다.";

    public CartItemNotFoundException() {
        super(MESSAGE);
    }
}
