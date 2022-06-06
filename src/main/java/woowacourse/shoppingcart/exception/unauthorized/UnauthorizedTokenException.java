package woowacourse.shoppingcart.exception.unauthorized;

import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.exception.ShoppingCartException;

public class UnauthorizedTokenException extends ShoppingCartException {

    public UnauthorizedTokenException() {
        super("998", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
    }
}
