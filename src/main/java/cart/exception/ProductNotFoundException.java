package cart.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CartCustomException {

    private static final String MESSAGE = "요청한 상품을 찾을 수 없습니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public ProductNotFoundException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
