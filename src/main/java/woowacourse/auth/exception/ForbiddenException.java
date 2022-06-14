package woowacourse.auth.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("접근 권한이 없는 사용자입니다.");
    }
}
