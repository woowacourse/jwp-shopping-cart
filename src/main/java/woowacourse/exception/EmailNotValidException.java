package woowacourse.exception;

public class EmailNotValidException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "인증 정보가 확인되지 않습니다";

    public EmailNotValidException() {
        super(DEFAULT_MESSAGE);
    }
}
