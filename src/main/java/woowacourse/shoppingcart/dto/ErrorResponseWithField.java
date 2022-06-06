package woowacourse.shoppingcart.dto;

public class ErrorResponseWithField {

    private final String field;
    private final String message;

    public ErrorResponseWithField(String field, String message) {
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
