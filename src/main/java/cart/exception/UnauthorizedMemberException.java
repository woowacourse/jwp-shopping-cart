package cart.exception;

public class UnauthorizedMemberException extends RuntimeException {

    public UnauthorizedMemberException(String message) {
        super(message);
    }
}
