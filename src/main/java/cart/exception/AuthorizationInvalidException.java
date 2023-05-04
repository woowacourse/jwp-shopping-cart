package cart.exception;

public class AuthorizationInvalidException extends RuntimeException {

    public AuthorizationInvalidException() {
        super("Basic 인증이 올바른지 확인해주세요.");
    }
}
