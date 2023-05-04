package cart.exception;

public class InvalidMemberException extends RuntimeException {
    public InvalidMemberException(final String errorMessage) {
        super(errorMessage);
    }
}
