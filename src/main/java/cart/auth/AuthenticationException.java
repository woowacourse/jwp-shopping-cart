package cart.auth;

public class AuthenticationException extends RuntimeException {
    private final String message = "사용자 인증에 실패했습니다.";

    @Override
    public String getMessage() {
        return message;
    }
}
