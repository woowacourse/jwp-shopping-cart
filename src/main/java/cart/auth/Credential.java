package cart.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Credential {
    private final String email;
    private final String password;
}
