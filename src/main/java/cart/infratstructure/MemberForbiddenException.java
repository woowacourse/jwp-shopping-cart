package cart.infratstructure;

public class MemberForbiddenException extends RuntimeException {

    public MemberForbiddenException(final String message) {
        super(message);
    }
}
