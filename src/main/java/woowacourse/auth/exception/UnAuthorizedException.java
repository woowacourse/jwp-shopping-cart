package woowacourse.auth.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException() {
        super("[ERROR] 인증이 되지 않은 유저입니다.");
    }
}
