package cart.global.exception.response;

public abstract class Response {

    protected final int statusCode;
    protected final String message;
    protected final String status;

    protected Response(final int statusCode, final String message, final String status) {
        this.statusCode = statusCode;
        this.message = message;
        this.status = status;
    }
}
