package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

@JsonTypeName("error")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ErrorResponse {

    @JsonProperty("message")
    private List<String> messages;

    private ErrorResponse() {
    }

    public ErrorResponse(final List<String> messages) {
        this.messages = messages;
    }

    public static ErrorResponse from(final RuntimeException exception) {
        return new ErrorResponse(List.of(exception.getMessage()));
    }

    public static ErrorResponse from(final String message) {
        return new ErrorResponse(List.of(message));
    }

    public static ErrorResponse from(final BindingResult bindingResult) {
        return new ErrorResponse(bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()));
    }

    public List<String> getMessages() {
        return messages;
    }
}
