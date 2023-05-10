package cart.web.handler;

import cart.exception.AuthorizationException;
import cart.exception.GlobalException;
import cart.web.controller.admin.dto.response.BadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<BadResponse> handleGlobalException(GlobalException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BadResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<BadResponse> handleAuthorizationException(AuthorizationException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new BadResponse("접근 권한이 없습니다."));
    }

    @ExceptionHandler
    public ResponseEntity<BadResponse> handleNumberFormatException(NumberFormatException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BadResponse("올바르지 않은 숫자 형식입니다."));
    }

    @ExceptionHandler
    public ResponseEntity<BadResponse> handleRuntimeException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BadResponse("예기치 못한 오류가 발생했습니다."));
    }
}
