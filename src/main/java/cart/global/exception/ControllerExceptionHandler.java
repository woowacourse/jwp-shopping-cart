package cart.global.exception;

import cart.global.exception.response.ErrorResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = new ErrorResponse(ExceptionStatus.BAD_INPUT_VALUE_EXCEPTION);
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        for (FieldError error : errors) {
            errorResponse.addValidation(error.getField(), error.getDefaultMessage());
        }

        return errorResponse;
    }
}
