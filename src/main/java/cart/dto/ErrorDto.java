package cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDto {

    private final String message;

    public ErrorDto(final String message, final String field) {
        this.message = String.format("%s (field: %s)", field);
    }
}
