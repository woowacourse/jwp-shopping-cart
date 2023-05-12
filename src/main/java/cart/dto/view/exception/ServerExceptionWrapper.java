package cart.dto.view.exception;

public class ServerExceptionWrapper {

    private final String message;

    private ServerExceptionWrapper(String message) {
        this.message = message;
    }

    public static ServerExceptionWrapper of(String message) {
        return new ServerExceptionWrapper(message);
    }

    public String getMessage() {
        return message;
    }
}
