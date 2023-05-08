package cart.exception;

public class UnAuthorizedCustomerException extends RuntimeException {

    public UnAuthorizedCustomerException(final String message) {
        super(message);
    }
}
