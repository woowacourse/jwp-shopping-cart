package cart.exception;

public class MemberForbiddenException extends RuntimeException {

    public MemberForbiddenException(final String message) {
        super(message);
    }
}
