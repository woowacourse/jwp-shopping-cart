package cart.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends CartException{

  public AuthenticationException(String message) {
    super(message, HttpStatus.UNAUTHORIZED);
  }
}
