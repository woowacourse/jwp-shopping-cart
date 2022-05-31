package woowacourse.shoppingcart.dto;

public class FieldErrorResponse {

    private String field;
    private String message;

    private FieldErrorResponse() {
    }

    public FieldErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
