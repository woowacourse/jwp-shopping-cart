package cart.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthInfo {
    private final String email;
    private final String password;
}
