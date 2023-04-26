package cart.exception;

import org.springframework.http.HttpStatus;

public enum ErrorStatus {

    NAME_RANGE_ERROR(HttpStatus.BAD_REQUEST, "상품의 이름은 최소 1자, 최대 50자까지 가능합니다."),
    PRICE_RANGE_ERROR(HttpStatus.BAD_REQUEST, "상품의 금액은 최소 10원, 최대 1억원 까지 가능합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorStatus(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
