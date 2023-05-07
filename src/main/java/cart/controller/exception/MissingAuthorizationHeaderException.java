package cart.controller.exception;

public class MissingAuthorizationHeaderException extends RuntimeException {
    public MissingAuthorizationHeaderException() {
        super("[ERROR] 인증 정보가 헤더에 없습니다.");
    }
}
