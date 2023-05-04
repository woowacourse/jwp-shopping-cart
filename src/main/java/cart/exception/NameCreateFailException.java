package cart.exception;

public class NameCreateFailException extends RuntimeException {

    public NameCreateFailException() {
        super("이름은 공백이 되면 안됩니다.");
    }
}
