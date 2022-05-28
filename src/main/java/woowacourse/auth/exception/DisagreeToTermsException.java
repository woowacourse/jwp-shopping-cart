package woowacourse.auth.exception;

public class DisagreeToTermsException extends RuntimeException {
    public DisagreeToTermsException() {
        super("약관을 동의하지 않은 유저는 생성될 수 없습니다.");
    }
}
