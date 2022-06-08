package woowacourse.common.ui;

import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.common.dto.ErrorResponse;
import woowacourse.common.dto.RedirectErrorResponse;
import woowacourse.common.exception.AuthenticationException;
import woowacourse.common.exception.ForbiddenException;
import woowacourse.common.exception.InvalidRequestException;
import woowacourse.common.exception.NotFoundException;
import woowacourse.common.exception.RedirectException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnhandledException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("예상치 못한 문제가 발생했습니다."));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidRequest(final MethodArgumentNotValidException bindingResult) {
        String causes = bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        String errorMessage = String.format("입력이 잘못되었습니다: [%s]", causes);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(InvalidRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(toErrorResponse(e));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(toErrorResponse(e));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(toErrorResponse(e));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(toErrorResponse(e));
    }

    @ExceptionHandler
    public ResponseEntity<RedirectErrorResponse> handle(RedirectException e) {
        return ResponseEntity.status(e.getStatus()).body(new RedirectErrorResponse(e));
    }

    private ErrorResponse toErrorResponse(Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}
