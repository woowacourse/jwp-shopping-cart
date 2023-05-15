package cart.controller.auth;

public class LoginBindingException extends RuntimeException {
    public LoginBindingException() {
        super("바인딩이 실패했습니다.");
    }
}
