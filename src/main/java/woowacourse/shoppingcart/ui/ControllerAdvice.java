package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.shoppingcart.exception.NotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleUnhandledException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body("Unhandled Exception");
    }
//
//    @ExceptionHandler(EmptyResultDataAccessException.class)
//    public ResponseEntity handle() {
//        return ResponseEntity.badRequest().body("존재하지 않는 데이터 요청입니다.");
//    }
//
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseEntity handleInvalidRequest(final BindingResult bindingResult) {
//        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//        final FieldError mainError = fieldErrors.get(0);
//
//        return ResponseEntity.badRequest().body(mainError.getDefaultMessage());
//    }
//
//    @ExceptionHandler({
//            HttpMessageNotReadableException.class,
//            ConstraintViolationException.class,
//    })
//    public ResponseEntity handleInvalidRequest(final RuntimeException e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
//
//    @ExceptionHandler({
//            InvalidCustomerException.class,
//            InvalidCartItemException.class,
//            InvalidProductException.class,
//            InvalidOrderException.class,
//            NotInCustomerCartItemException.class,
//    })
//    public ResponseEntity handleInvalidAccess(final RuntimeException e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }

    @ExceptionHandler
    public ResponseEntity<String> handle(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
