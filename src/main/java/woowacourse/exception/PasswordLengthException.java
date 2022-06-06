package woowacourse.exception;

public class PasswordLengthException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "비밀번호 길이는 8자 이상이여야 합니다.";

    public PasswordLengthException() {
        super(DEFAULT_MESSAGE);
    }
}
