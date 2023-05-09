package cart.exception.auth;

public class UnauthenticatedException extends RuntimeException {

    public UnauthenticatedException() {
        super("로그인에 실패했습니다.");
    }
}
