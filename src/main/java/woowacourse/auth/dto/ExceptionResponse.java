package woowacourse.auth.dto;

public class ExceptionResponse {

    private final String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public static ExceptionResponse from(Exception e) {
        return new ExceptionResponse(e.getMessage());
    }

    public String getMessage() {
        return message;
    }
}
