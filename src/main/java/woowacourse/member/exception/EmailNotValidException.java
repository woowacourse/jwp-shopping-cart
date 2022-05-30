package woowacourse.member.exception;

public class EmailNotValidException extends BadRequestException {

    public EmailNotValidException() {
        super("[ERROR] 이메일이 올바르지 않습니다.");
    }
}
