package cart.controller.exception;

public class MemberNotValidException extends IllegalArgumentException {

    public MemberNotValidException(final String message) {
        super(message);
    }
}
