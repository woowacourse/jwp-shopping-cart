package cart.repository.exception;

import java.util.NoSuchElementException;

public class NoSuchProductException extends NoSuchElementException {

    public NoSuchProductException() {
        super("없는 상품입니다");
    }
}
