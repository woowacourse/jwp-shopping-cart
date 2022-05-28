package woowacourse.auth.exception;

public class InvalidPasswordFormatException extends RuntimeException {

    public InvalidPasswordFormatException() {
        super("올바르지 않은 패스워드 입력 형식입니다.");
    }
}
