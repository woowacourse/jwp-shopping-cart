package cart.exception.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ExceptionResponse {
    private final String message;
    
    public ExceptionResponse(final String message) {
        this.message = message;
    }
}
