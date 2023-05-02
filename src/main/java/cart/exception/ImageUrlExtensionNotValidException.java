package cart.exception;

import org.springframework.http.HttpStatus;

public class ImageUrlExtensionNotValidException extends CartCustomException {

    private static final String MESSAGE = "유효한 이미지 URL이 아닙니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public ImageUrlExtensionNotValidException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
