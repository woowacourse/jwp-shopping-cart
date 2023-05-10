package cart.exception;

import cart.dto.ErrorDto;
import java.util.Arrays;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDto handleException(ConstraintViolationException exception) {
        log.error("ConstraintViolationException message={}", exception.getMessage());
        exception.printStackTrace();
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorDto handleException(IllegalArgumentException exception) {
        log.error("IllegalArgumentException message={}", exception.getMessage());
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorDto handleException(AuthenticationException exception) {
        log.error("AuthenticationException message={}", exception.getMessage());
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MemberForbiddenException.class)
    public ErrorDto handleException(MemberForbiddenException exception) {
        log.error("MemberForbiddenException message={}", exception.getMessage());
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto handleException(Exception exception) {
        log.error(exception.getClass().getName() + " message={}", exception.getMessage());
        log.error(Arrays.toString(exception.getStackTrace()));
        return new ErrorDto("internal server error");
    }
}
