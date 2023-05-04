package cart.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends CartException {

  public InternalServerException(String message) {
    super(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
