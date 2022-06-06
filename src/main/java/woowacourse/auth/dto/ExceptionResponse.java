package woowacourse.auth.dto;

public class ExceptionResponse {

    private String message;

    private ExceptionResponse() {

    }

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
