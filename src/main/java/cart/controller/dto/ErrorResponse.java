package cart.controller.dto;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorResponse {

    private final List<String> messages;

    public ErrorResponse(final List<String> messages) {
        this.messages = messages;
    }

    public static ErrorResponse of(final BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> filedErrorMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ErrorResponse(filedErrorMessages);
    }

    public List<String> getMessages() {
        return messages;
    }

}
