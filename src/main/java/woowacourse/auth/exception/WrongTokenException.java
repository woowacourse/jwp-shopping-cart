package woowacourse.auth.exception;

import woowacourse.exception.BadRequestException;

public class WrongTokenException extends BadRequestException {

    public WrongTokenException() {
        super("[ERROR] 토큰이 올바르지 않습니다.");
    }
}
