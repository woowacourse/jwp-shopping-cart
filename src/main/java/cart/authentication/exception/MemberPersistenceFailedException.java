package cart.authentication.exception;

import cart.config.PersistenceException;

public class MemberPersistenceFailedException extends PersistenceException {
    public MemberPersistenceFailedException(String message) {
        super(message);
    }
}

