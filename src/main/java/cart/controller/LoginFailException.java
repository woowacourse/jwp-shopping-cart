package cart.controller;

public class LoginFailException extends IllegalArgumentException {

    private static final String MESSAGE = "email과 password가 일치하지 않습니다";

    public LoginFailException() {
        super(MESSAGE);
    }
}
