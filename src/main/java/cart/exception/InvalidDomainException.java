package cart.exception;

public abstract class InvalidDomainException extends RuntimeException {
    public InvalidDomainException(final String errorMessage) {
        super(errorMessage);
    }
}
