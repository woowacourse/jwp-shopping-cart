package cart.exception;

import org.springframework.http.HttpStatus;

public class ProductNameLengthOverException extends CartCustomException {

    private static final String MESSAGE = "상품 이름의 길이가 1~255여야 합니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public ProductNameLengthOverException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
