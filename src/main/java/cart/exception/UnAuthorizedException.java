package cart.exception;

public final class UnAuthorizedException extends GlobalException {

    private static final String message = "인증되지 않은 사용자입니다.";

    public UnAuthorizedException() {
        super(message);
    }
}
