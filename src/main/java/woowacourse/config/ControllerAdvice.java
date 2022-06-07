package woowacourse.config;

import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.exception.BadRequestException;
import woowacourse.exception.NotFoundException;
import woowacourse.exception.UnauthorizedException;
import woowacourse.shoppingcart.ui.dto.response.ExceptionResponse;

@RestControllerAdvice
public class ControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequest(BadRequestException e) {
        logger.error(e.toString());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorized(UnauthorizedException e) {
        logger.error(e.toString());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionResponse> notFound(NotFoundException e) {
        logger.error(e.toString());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtime(RuntimeException e) {
        logger.error(e.toString());
        return ResponseEntity.badRequest().body(new ExceptionResponse("확인되지 않은 오류가 발생했어요.. 잠시 후에 다시 시도해주시겠어요? 🥲"));
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ExceptionResponse> requestValidationException(BindException e) {
        logger.error(e.toString());
        final String errorMessage = parseErrorMessage(e);

        return new ResponseEntity<>(new ExceptionResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }

    private String parseErrorMessage(BindException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> ((FieldError) error).getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
