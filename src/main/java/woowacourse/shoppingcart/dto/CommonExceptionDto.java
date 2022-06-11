package woowacourse.shoppingcart.dto;

public class CommonExceptionDto {

    private String message;

    public CommonExceptionDto() {
    }

    public CommonExceptionDto(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
