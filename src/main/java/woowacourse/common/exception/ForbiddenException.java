package woowacourse.common.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException() {
        this("로그인이 필요합니다.");
    }
}
