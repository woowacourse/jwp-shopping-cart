package cart.domain.exception;

public class UnexpectedDomainException extends RuntimeException {

    public UnexpectedDomainException() {
    }

    public UnexpectedDomainException(String message) {
        super(message);
    }
}
