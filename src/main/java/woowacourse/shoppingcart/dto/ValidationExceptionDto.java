package woowacourse.shoppingcart.dto;

public class ValidationExceptionDto {

    private String field;
    private String message;

    private ValidationExceptionDto() {
    }

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
