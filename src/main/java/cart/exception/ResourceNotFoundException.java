package cart.exception;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Long resourceId) {
        super(message + System.lineSeparator() + "id : " + resourceId);
    }
}
