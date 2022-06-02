package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class DuplicatedNameException extends ShoppingCartException {
    public DuplicatedNameException() {
        this("이미 가입된 이름이 있습니다.");
    }

    public DuplicatedNameException(String name) {
        super(name, HttpStatus.BAD_REQUEST);
    }
}
