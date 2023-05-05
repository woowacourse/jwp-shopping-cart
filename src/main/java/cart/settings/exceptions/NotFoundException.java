package cart.settings.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String item) {
        super(item + " not found");
    }
}
