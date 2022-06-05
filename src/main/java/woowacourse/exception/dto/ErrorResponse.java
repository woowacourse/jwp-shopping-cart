package woowacourse.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.Arrays;

@JsonFormat(shape = Shape.OBJECT)
public enum ErrorResponse {
    DUPLICATED_EMAIL(1001, "Duplicated Email"),
    LOGIN_FAIL(2001, "Login Fail"),
    INCORRECT_PASSWORD(3001, "Incorrect Password"),
    INVALID_TOKEN(3002, "Invalid Token"),
    INVALID_EMAIL(4001, "Invalid Email"),
    INVALID_PASSWORD(4002, "Invalid Password"),
    INVALID_USERNAME(4003, "Invalid Username");

    private final int errorCode;
    private final String message;

    ErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorResponse from(int errorCode) {
        return Arrays.stream(values())
                .filter(it -> it.errorCode == errorCode)
                .findFirst()
                .orElseThrow();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
