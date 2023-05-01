package cart.exception;

public class PasswordCreateFailException extends RuntimeException {

    public PasswordCreateFailException() {
        super("패스워드는 특수문자, 영문, 숫자를 모두 포함하여 8자 이상으로 작성해주세요.");
    }
}
