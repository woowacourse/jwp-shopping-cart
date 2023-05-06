package cart.common.exceptions;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(final String message) {
        super(message);
    }
}
