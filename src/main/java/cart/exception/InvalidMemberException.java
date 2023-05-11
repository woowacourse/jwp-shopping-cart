package cart.exception;

public class InvalidMemberException extends InvalidDomainException {
    public InvalidMemberException(final String errorMessage) {
        super(errorMessage);
    }
}
