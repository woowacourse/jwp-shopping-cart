package woowacourse.shoppingcart.exception;

import io.jsonwebtoken.JwtException;

public class PayloadNotFoundException extends JwtException {

    public PayloadNotFoundException() {
        super("토큰의 페이로드를 찾을 수 없습니다.");
    }
}
