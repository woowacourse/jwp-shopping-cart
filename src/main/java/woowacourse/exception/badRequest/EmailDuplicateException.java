package woowacourse.exception.badRequest;

public class EmailDuplicateException extends BadRequestException  {
    private static final String DEFAULT_MESSAGE = "이미 존재하는 이메일 입니다.";

    public EmailDuplicateException() {
        super(DEFAULT_MESSAGE);
    }
}
