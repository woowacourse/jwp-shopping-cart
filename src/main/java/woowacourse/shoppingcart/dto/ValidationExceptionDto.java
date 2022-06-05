package woowacourse.shoppingcart.dto;

public class ValidationExceptionDto {

    private final String field;
    private final String message;

    public ValidationExceptionDto(final String field, final String message) {
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
