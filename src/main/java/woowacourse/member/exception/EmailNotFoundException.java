package woowacourse.member.exception;

public class EmailNotFoundException extends IllegalArgumentException {

    public EmailNotFoundException(String message) {
        super(message);
    }
}
