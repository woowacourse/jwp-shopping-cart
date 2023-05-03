package cart.auth.excpetion;

public class AuthorizeException extends RuntimeException {

    public AuthorizeException(String message) {
        super(message);
    }
}
