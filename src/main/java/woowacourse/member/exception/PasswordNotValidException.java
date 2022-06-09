package woowacourse.member.exception;

import woowacourse.exception.BadRequestException;

public class PasswordNotValidException extends BadRequestException {

    public PasswordNotValidException() {
        super("[ERROR] 비밀번호가 올바르지 않습니다.");
    }
}
