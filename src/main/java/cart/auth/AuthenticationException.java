package cart.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationException extends RuntimeException {

    private final String message;
}
