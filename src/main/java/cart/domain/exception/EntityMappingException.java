package cart.domain.exception;

public class EntityMappingException extends RuntimeException {

    public EntityMappingException() {
    }

    public EntityMappingException(String message) {
        super(message);
    }
}
