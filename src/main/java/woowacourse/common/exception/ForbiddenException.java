package woowacourse.common.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException() {
        this("접근 권한이 없습니다.");
    }
}
