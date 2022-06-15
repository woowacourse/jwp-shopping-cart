package woowacourse.exception.badRequest;

public class PasswordInvalidException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "비밀번호 길이는 8자 이상이고 특수문자와 알파벳 문자가 하나 이상 포함되어야 합니다.";

    public PasswordInvalidException() {
        super(DEFAULT_MESSAGE);
    }
}
