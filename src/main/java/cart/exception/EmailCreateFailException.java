package cart.exception;

public class EmailCreateFailException extends RuntimeException {

    public EmailCreateFailException() {
        super("이메일의 형식이 맞지 않습니다. 예시) example@example.com");
    }
}
