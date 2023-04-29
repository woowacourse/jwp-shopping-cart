package cart.controller;

import cart.controller.dto.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = MainPageController.class)
public class MainPageControllerAdvice {

    Logger log = LoggerFactory.getLogger(MainPageControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public String handlerException(final Exception exception, final Model model) {
        log.error(exception.getMessage());
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                internalServerError.value(),
                internalServerError.getReasonPhrase(),
                "예기치 못한 오류가 발생했습니다.");
        model.addAttribute("error", exceptionResponse);
        return "error";
    }
}
