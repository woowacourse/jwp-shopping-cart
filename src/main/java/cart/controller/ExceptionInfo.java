package cart.controller;

import cart.exception.CartCustomException;
import cart.exception.ImageUrlExtensionNotValidException;
import cart.exception.NegativePriceException;
import cart.exception.ProductNotFoundException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;

public enum ExceptionInfo {

    PRODUCT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "요청한 상품을 찾을 수 없습니다."
            , ProductNotFoundException.class),
    IMAGE_URL_EXTENSION_NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "유효한 이미지 URL이 아닙니다.",
            ImageUrlExtensionNotValidException.class),
    NEGATIVE_PRICE_EXCEPTION(HttpStatus.BAD_REQUEST, "가격은 음수일 수 없습니다.",
            NegativePriceException.class),
    PRODUCT_NAME_LENGTH_OVER_EXCEPTION(HttpStatus.BAD_REQUEST, "상품 이름의 길이가 1~255여야 합니다.",
            ProductNotFoundException.class);

    private final HttpStatus errorCode;
    private final String message;
    private final Class<? extends CartCustomException> exception;

    ExceptionInfo(final HttpStatus errorCode, final String message,
                  final Class<? extends CartCustomException> exception) {
        this.errorCode = errorCode;
        this.message = message;
        this.exception = exception;
    }

    public static ExceptionInfo from(Class<?> type) {
        return Arrays.stream(ExceptionInfo.values())
                .filter(info -> info.exception.equals(type))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
