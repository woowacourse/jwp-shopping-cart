package woowacourse.shoppingcart.ui;

import static woowacourse.shoppingcart.exception.ExceptionMessage.CODE_1001;
import static woowacourse.shoppingcart.exception.ExceptionMessage.CODE_2001;
import static woowacourse.shoppingcart.exception.ExceptionMessage.CODE_2102;
import static woowacourse.shoppingcart.exception.ExceptionMessage.CODE_2103;
import static woowacourse.shoppingcart.exception.ExceptionMessage.CODE_2201;
import static woowacourse.shoppingcart.exception.ExceptionMessage.CODE_3001;

import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.shoppingcart.dto.exception.ExceptionDto;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;
import woowacourse.shoppingcart.exception.IncorrectPasswordException;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidEmailFormatException;
import woowacourse.shoppingcart.exception.InvalidNicknameFormatException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.InvalidPasswordFormatException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@RestControllerAdvice(basePackages = "woowacourse.shoppingcart")
public class CartControllerAdvice {

    @ExceptionHandler(InvalidEmailFormatException.class)
    public ResponseEntity<ExceptionDto> handleInvalidEmail(ProductNotFoundException e) {
        return ResponseEntity.badRequest().body(new ExceptionDto(CODE_1001.getCode(), CODE_1001.getMessage()));
    }

    @ExceptionHandler(InvalidNicknameFormatException.class)
    public ResponseEntity<ExceptionDto> handleInvalidNickname(InvalidNicknameFormatException e) {
        return ResponseEntity.badRequest().body(new ExceptionDto(CODE_2102.getCode(), CODE_2102.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordFormatException.class)
    public ResponseEntity<ExceptionDto> handleInvalidPassword(InvalidPasswordFormatException e) {
        return ResponseEntity.badRequest().body(new ExceptionDto(CODE_2103.getCode(), CODE_2103.getMessage()));
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<ExceptionDto> handleDuplicatedEmail(DuplicatedEmailException e) {
        return ResponseEntity.badRequest().body(new ExceptionDto(CODE_2001.getCode(), CODE_2001.getMessage()));
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ExceptionDto> handleIncorrectPassword(IncorrectPasswordException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionDto(CODE_2201.getCode(), CODE_2201.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleProductNotFound(ProductNotFoundException e) {
        return ResponseEntity.badRequest().body(new ExceptionDto(CODE_3001.getCode(), CODE_3001.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleInvalidRequest(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldError mainError = fieldErrors.get(0);

        return ResponseEntity.badRequest().body(mainError.getDefaultMessage());
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<String> handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
            InvalidCustomerException.class,
            InvalidCartItemException.class,
            InvalidProductException.class,
            InvalidOrderException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity<String> handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
            InvalidAuthException.class
    })
    public ResponseEntity<String> handleUnauthorizedAccess(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleUnhandledException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handle() {
        return ResponseEntity.badRequest().body("존재하지 않는 데이터 요청입니다.");
    }
}
