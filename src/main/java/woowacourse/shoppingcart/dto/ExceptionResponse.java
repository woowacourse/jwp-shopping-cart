package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "error")
public class ExceptionResponse {
    private final String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
