package woowacourse.shoppingcart.ui;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.badrequest.BadRequestException;
import woowacourse.shoppingcart.exception.notfound.NotFoundException;
import woowacourse.shoppingcart.exception.unauthorized.UnauthorizedException;

@RestControllerAdvice
public class ControllerAdvice {

    private static final int INVALID_FORMAT_ERROR_CODE = 1000;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnhandledException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(final BadRequestException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleInvalidRequest(MethodArgumentNotValidException e) {
        String message = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .findAny()
                .orElse("잘못된 형식입니다.");
        return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_FORMAT_ERROR_CODE, message));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Void> handleUnauthorizedException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    //TODO: dao에서 찾지 못하면 Optional로 반환하고 Service에서 예외를 던지도록 수정
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handle() {
        return ResponseEntity.badRequest().body(new ErrorResponse(0, "존재하지 않는 데이터 요청입니다."));
    }

    //TODO: BadRequestException으로 상속하여 사용할 수 있도록 수정
    @ExceptionHandler({
            InvalidCartItemException.class,
            InvalidOrderException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidAccess(final RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
    }
}
