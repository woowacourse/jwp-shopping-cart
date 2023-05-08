package cart.controller;

import cart.controller.dto.ErrorResponse;
import cart.exception.UserAuthorizationException;
import cart.exception.UserForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = CartApiController.class)
public class AuthControllerAdvice {

    private final static String WWW_AUTH_HEADER_KEY = "WWW-Authenticate";
    private final static String REQUIRED_AUTH_TYPE = "Basic";

    @ExceptionHandler(UserAuthorizationException.class)
    public ResponseEntity<ErrorResponse> unauthorizedHandler(final UserAuthorizationException exception) {
        final String message = exception.getMessage();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header(WWW_AUTH_HEADER_KEY, REQUIRED_AUTH_TYPE)
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler(UserForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenHandler(final UserForbiddenException exception) {
        final String message = exception.getMessage();

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(message));
    }
}
