package woowacourse.member.exception;

import woowacourse.exception.BadRequestException;

public class EmailNotValidException extends BadRequestException {

    public EmailNotValidException() {
        super("[ERROR] 이메일이 올바르지 않습니다.");
    }
}
