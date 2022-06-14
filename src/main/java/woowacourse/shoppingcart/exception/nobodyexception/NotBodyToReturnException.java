package woowacourse.shoppingcart.exception.nobodyexception;

import org.springframework.http.HttpStatus;

public class NotBodyToReturnException extends RuntimeException {

    private final HttpStatus httpStatus;

    public NotBodyToReturnException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
