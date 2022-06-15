package woowacourse;

import java.util.List;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.exception.badRequest.BadRequestException;
import woowacourse.exception.notFound.NotFoundException;
import woowacourse.exception.unauthorization.UnauthorizedException;
import woowacourse.shoppingcart.dto.response.ExceptionResponse;

@RestControllerAdvice
public class ControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleUnhandledException(RuntimeException e) {
        log.error("Error", e);
        return ResponseEntity.internalServerError().body(new ExceptionResponse("서버 내부 오류가 발생하였습니다."));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().body("존재하지 않는 데이터 요청입니다.");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleInvalidRequest(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldError mainError = fieldErrors.get(0);

        return ResponseEntity.badRequest().body(mainError.getDefaultMessage());
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
