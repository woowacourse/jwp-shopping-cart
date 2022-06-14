package woowacourse.shoppingcart.dto;

public class ErrorResponseWithField {

    private String field;
    private String message;

    private ErrorResponseWithField() {
    }

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
