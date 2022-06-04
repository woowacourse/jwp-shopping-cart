package woowacourse.shoppingcart.ui.dto;

public class ExceptionResponse {

    public final String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public static ExceptionResponse from(RuntimeException e) {
        return new ExceptionResponse(e.getMessage());
    }
}
