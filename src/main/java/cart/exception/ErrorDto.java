package cart.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ErrorDto {

    private final int status;
    private final String message;
    private final String path;
}
