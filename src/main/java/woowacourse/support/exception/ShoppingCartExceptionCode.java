package woowacourse.support.exception;

import org.springframework.http.HttpStatus;

public interface ShoppingCartExceptionCode {

    long getCode();

    String getMessage();

    HttpStatus getHttpStatus();
}
