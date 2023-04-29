package cart.exception;

import cart.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ExceptionDto> handleException(MethodArgumentNotValidException exception) {
        return exception.getFieldErrors().stream().map(fieldError -> String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage())).map(ExceptionDto::new).collect(Collectors.toUnmodifiableList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class})
    public ExceptionDto handleException(Exception exception) {
        log.error("exception.getClass()={}", exception.getClass());
        return new ExceptionDto(exception.getMessage());
    }
}
