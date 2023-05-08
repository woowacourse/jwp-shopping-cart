package cart.controller.exceptionhandler;

import cart.auth.UnauthorizedException;
import cart.domain.exception.WrongEmailFormatException;
import cart.domain.exception.WrongPasswordFormatException;
import cart.domain.exception.WrongPriceException;
import cart.domain.exception.WrongProductNameException;
import cart.service.exception.DuplicateCartException;
import cart.service.exception.InvalidEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CartExceptionHandler {

    private static final String UNEXPECTED_EXCEPTION_MESSAGE = "관리자에게 문의하세요.";
    private static final String LANGUAGE_PARAM = "lan";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodValidException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(WebRequest request, UnauthorizedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getExceptionMessageByLanguage(request, exception));
    }

    @ExceptionHandler(DuplicateCartException.class)
    public ResponseEntity<String> handleDuplicateCartException(WebRequest request, DuplicateCartException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(getExceptionMessageByLanguage(request, exception));
    }

    @ExceptionHandler({WrongEmailFormatException.class, WrongPasswordFormatException.class, WrongPriceException.class,
            WrongProductNameException.class, InvalidEmailException.class})
    public ResponseEntity<String> handleWrongValueException(WebRequest request, CustomException exception) {
        return ResponseEntity.badRequest().body(getExceptionMessageByLanguage(request, exception));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnexpectedException(RuntimeException exception) {
        return ResponseEntity.internalServerError().body(UNEXPECTED_EXCEPTION_MESSAGE);
    }

    private String getExceptionMessageByLanguage(WebRequest request, CustomException exception) {
        String lan = request.getParameter(LANGUAGE_PARAM);
        return exception.findMessageByLanguage(lan);
    }
}
