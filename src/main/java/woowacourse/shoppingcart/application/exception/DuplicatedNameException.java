package woowacourse.shoppingcart.application.exception;

public final class DuplicatedNameException extends BusinessException {
    public DuplicatedNameException() {
        this("이미 가입된 이름이 있습니다.");
    }

    public DuplicatedNameException(String name) {
        super(name);
    }
}
