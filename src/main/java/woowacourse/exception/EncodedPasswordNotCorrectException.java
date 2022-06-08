package woowacourse.exception;

public class EncodedPasswordNotCorrectException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "암호화된 비밀번호가 아닙니다.";

    public EncodedPasswordNotCorrectException() {
        super(DEFAULT_MESSAGE);
    }
}
