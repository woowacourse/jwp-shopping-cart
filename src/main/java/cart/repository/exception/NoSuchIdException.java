package cart.repository.exception;

import java.util.NoSuchElementException;

public class NoSuchIdException extends NoSuchElementException {
    public NoSuchIdException() {
        super("없는 대상입니다");
    }
}
