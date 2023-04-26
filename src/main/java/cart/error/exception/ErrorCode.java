package cart.error.exception;

public enum ErrorCode {

    CANT_SELL_NEGATIVE_QUANTITY(400, "PRODUCT-400-1", "Product can't be sold at a negative price."),
    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "Internal Server Error");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
