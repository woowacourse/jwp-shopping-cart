package cart.exception;

public class MemberException extends RuntimeException{

    private final String message;

    public MemberException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
