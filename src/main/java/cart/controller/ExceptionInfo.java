package cart.controller;

import cart.exception.CartCustomException;
import cart.exception.ProductNotFoundException;
import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;

public enum ExceptionInfo {

    PRODUCT_NOT_FOUND_EXCEPTION(HttpServletResponse.SC_NOT_FOUND, "요청한 상품을 찾을 수 없습니다."
            , ProductNotFoundException.class);

    private final int errorCode;
    private final String message;
    private final Class<? extends CartCustomException> exception;

    ExceptionInfo(final int errorCode, final String message, final Class<? extends CartCustomException> exception) {
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

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
