package cart.exception;

import org.springframework.dao.DataAccessException;

public class ProductNotFoundException extends DataAccessException {

    private final static String MESSAGE = "존재하지 않는 상품입니다.";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
