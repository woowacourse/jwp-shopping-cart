package cart.dto.response;

public class SimpleResponse extends Response {
    public SimpleResponse(String code, String message) {
        super(code, message);
    }

    public static SimpleResponse ok(String message) {
        return new SimpleResponse("200", message);
    }

    public static SimpleResponse badRequest(String message) {
        return new SimpleResponse("400", message);
    }

    public static SimpleResponse internalServerError(String message) {
        return new SimpleResponse("500", message);
    }
}
