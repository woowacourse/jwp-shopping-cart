package woowacourse.shoppingcart.auth.ui;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import woowacourse.shoppingcart.auth.dto.ExceptionResponse;
import woowacourse.shoppingcart.auth.exception.InvalidTokenException;
import woowacourse.shoppingcart.auth.exception.LoginFailedException;
import woowacourse.shoppingcart.exception.WrongPasswordException;

@RestControllerAdvice(basePackageClasses = AuthController.class)
public class AuthControllerAdvice {

    @ExceptionHandler({NoSuchElementException.class, WrongPasswordException.class})
    public ResponseEntity<ExceptionResponse> handleLoginFailedException() {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new LoginFailedException().getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidTokenException(InvalidTokenException e) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
}
