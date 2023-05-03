package cart.domain.exception;

public class DbNotAffectedException extends RuntimeException {

    public DbNotAffectedException() {

    }

    public DbNotAffectedException(String message) {
        super(message);
    }
}
