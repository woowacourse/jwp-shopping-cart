package woowacourse.shoppingcart.dto;

public class ExceptionRequest {

    private String message;

    public ExceptionRequest() {
    }

    public ExceptionRequest(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
