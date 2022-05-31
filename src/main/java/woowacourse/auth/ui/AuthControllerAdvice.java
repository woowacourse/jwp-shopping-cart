package woowacourse.auth.ui;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import woowacourse.auth.dto.ExceptionResponse;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.exception.LoginFailedException;
import woowacourse.shoppingcart.exception.WrongPasswordException;

@RestControllerAdvice
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException() {
        return new ResponseEntity<>(new ExceptionResponse("형식에 맞지 않은 입력입니다."), HttpStatus.BAD_REQUEST);
    }
}
