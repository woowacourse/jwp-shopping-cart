package woowacourse.shoppingcart.dto;

public class CommonExceptionDto {

    private final String message;

    public CommonExceptionDto(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
