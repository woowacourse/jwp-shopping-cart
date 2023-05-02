package cart.web.handler;

import cart.exception.ErrorCode;
import cart.exception.ErrorResponse;
import cart.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<ErrorCode, String> errorEntry = new EnumMap<>(ErrorCode.class);

    {
        errorEntry.put(ErrorCode.INVALID_CATEGORY, "상품 정보를 찾을 수 없습니다.");
        errorEntry.put(ErrorCode.PRODUCT_NOT_FOUND, "삭제할 수 없습니다. 관리자에게 문의하세요.");
        errorEntry.put(ErrorCode.INVALID_DELETE, "유효하지 않은 카테고리입니다.");
        errorEntry.put(ErrorCode.USER_NOT_FOUND, "해당 유저가 존재하지 않습니다.");
        errorEntry.put(ErrorCode.INVALID_REQUEST, "");
        errorEntry.put(ErrorCode.UNEXPECTED_EXCEPTION, "예상치 못한 예외가 발생했습니다. 잠시만 기다려주세요.");
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> globalException(final GlobalException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = new ErrorResponse(errorCode, List.of(errorEntry.get(errorCode)));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final List<String> errorMessage = getErrorMessage(e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_REQUEST, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unExpectedException(final Exception e) {
        log.error("error", e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNEXPECTED_EXCEPTION, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<String> getErrorMessage(final MethodArgumentNotValidException e) {
        return e.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
