package cart.exception;

public class InvalidProductException extends InvalidDomainException {

    public InvalidProductException(final String errorMessage) {
        super(errorMessage);
    }
}
