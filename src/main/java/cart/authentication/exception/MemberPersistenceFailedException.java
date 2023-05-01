package cart.authentication.exception;

import cart.common.PersistenceException;
import cart.common.PersistenceExceptionMessages;

public class MemberPersistenceFailedException extends PersistenceException {
    public MemberPersistenceFailedException(PersistenceExceptionMessages message) {
        super(message);
    }
}

