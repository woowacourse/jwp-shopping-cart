package cart.controller;

import cart.dto.view.exception.AuthorizationExceptionWrapper;
import cart.dto.view.exception.IllegalStateExceptionWrapper;
import cart.dto.view.exception.MethodArgumentNotValidExceptionWrapper;
import cart.dto.view.exception.ServerExceptionWrapper;
import cart.ui.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerExceptionWrapper> handler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ServerExceptionWrapper.of("서버오류"));
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<AuthorizationExceptionWrapper> handler(AuthorizationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(AuthorizationExceptionWrapper.of("권한이 없습니다."));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<IllegalStateExceptionWrapper> handler(IllegalStateException exception) {
        return ResponseEntity.badRequest()
                .body(IllegalStateExceptionWrapper.of(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentNotValidExceptionWrapper> handler(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        StringBuilder errMessage = new StringBuilder();

        for (FieldError error : result.getFieldErrors()) {
            errMessage.append("[")
                    .append(error.getField())
                    .append("] ")
                    .append(":")
                    .append(error.getDefaultMessage());
        }
        return ResponseEntity.badRequest()
                .body(MethodArgumentNotValidExceptionWrapper.of(errMessage.toString()));
    }
}
