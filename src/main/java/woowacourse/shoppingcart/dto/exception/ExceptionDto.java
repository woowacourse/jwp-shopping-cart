package woowacourse.shoppingcart.dto.exception;

public class ExceptionDto {
    private int code;
    private String message;

    public ExceptionDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
