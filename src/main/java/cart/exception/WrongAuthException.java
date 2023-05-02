package cart.exception;

public class WrongAuthException extends RuntimeException {

    public WrongAuthException() {
    }

    @Override
    public String getMessage() {
        return "로그인 정보가 잘못되었습니다.";
    }
}
