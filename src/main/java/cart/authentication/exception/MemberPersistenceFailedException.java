package cart.authentication.exception;

import cart.common.exception.PersistenceException;
import cart.common.exception.PersistenceExceptionMessages;

public class MemberPersistenceFailedException extends PersistenceException {
    public MemberPersistenceFailedException(PersistenceExceptionMessages message) {
        super(message);
    }
}

