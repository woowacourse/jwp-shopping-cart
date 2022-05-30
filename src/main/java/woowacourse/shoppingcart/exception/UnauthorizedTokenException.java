package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedTokenException extends ShoppingCartException {

    public UnauthorizedTokenException() {
        super("998", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
    }
}
