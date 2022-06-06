package woowacourse.exception;

public class EmailFormattingException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "이메일 형식이 맞지 않습니다";

    public EmailFormattingException() {
        super(DEFAULT_MESSAGE);
    }
}
