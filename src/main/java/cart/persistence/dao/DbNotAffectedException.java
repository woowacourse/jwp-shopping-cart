package cart.persistence.dao;

public class DbNotAffectedException extends RuntimeException {

    DbNotAffectedException() {

    }

    DbNotAffectedException(String message) {
        super(message);
    }
}
