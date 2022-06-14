package woowacourse.shoppingcart.exception;

public class ExceptionDto {

    private final String message;

    public ExceptionDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
