package woowacourse.member.exception;

import woowacourse.exception.BadRequestException;

public class WrongPasswordException extends BadRequestException {

    public WrongPasswordException() {
        super("[ERROR] 비밀번호가 틀렸습니다.");
    }
}
