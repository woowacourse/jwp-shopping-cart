package cart.service;

public class DbNotAffectedException extends RuntimeException {

    DbNotAffectedException() {

    }

    DbNotAffectedException(String message) {
        super(message);
    }
}
