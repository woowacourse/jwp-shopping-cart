package woowacourse.shoppingcart.application.exception;

public final class NotMatchPasswordException extends BusinessException {

    public NotMatchPasswordException() {
        this("비밀번호가 일치하지 않습니다.");
    }

    public NotMatchPasswordException(String message) {
        super(message);
    }
}
