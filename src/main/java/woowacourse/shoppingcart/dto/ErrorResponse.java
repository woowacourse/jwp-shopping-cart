package woowacourse.shoppingcart.dto;

import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

public class ErrorResponse {

    private String message;

    private ErrorResponse() {
    }

    private ErrorResponse(final String message) {
        this.message = message;
    }

    public static ErrorResponse from(final String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse from(final Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

    public static ErrorResponse from(final BindingResult bindingResult) {
        return new ErrorResponse(bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList())
                .get(0));
    }

    public String getMessage() {
        return message;
    }
}
