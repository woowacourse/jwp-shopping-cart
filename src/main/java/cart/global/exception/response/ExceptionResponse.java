package cart.global.exception.response;

import cart.global.exception.common.BusinessException;
import cart.global.exception.common.ExceptionStatus;
import org.springframework.validation.BindingResult;

import java.util.List;

public class ExceptionResponse {

    private final int statusCode;
    private final String message;
    private final String status;
    private final List<BindingResultExceptionResponse> errors;

    private ExceptionResponse(final int statusCode, final String message,
                              final String httpStatus, final List<BindingResultExceptionResponse> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.status = httpStatus;
        this.errors = errors;
    }

    public static ExceptionResponse convertFrom(final ExceptionStatus exceptionStatus, BindingResult bindingResult) {
        return new ExceptionResponse(exceptionStatus.getStatus(),
                                     exceptionStatus.getMessage(),
                                     exceptionStatus.getHttpStatus().name(),
                                     BindingResultExceptionResponse.from(bindingResult));
    }

    public static ExceptionResponse convertFrom(final ExceptionStatus exceptionStatus) {
        return new ExceptionResponse(exceptionStatus.getStatus(),
                                     exceptionStatus.getMessage(),
                                     exceptionStatus.getHttpStatus().name(),
                                     null);
    }

    public static ExceptionResponse convertFrom(final BusinessException businessException) {
        return new ExceptionResponse(businessException.getStatus(),
                                     businessException.getMessage(),
                                     businessException.getHttpStatus().name(),
                                     null
        );
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<BindingResultExceptionResponse> getErrors() {
        return errors;
    }
}
