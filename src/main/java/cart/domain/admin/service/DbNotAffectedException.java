package cart.domain.admin.service;

public class DbNotAffectedException extends RuntimeException {

    DbNotAffectedException() {

    }

    DbNotAffectedException(String message) {
        super(message);
    }
}
