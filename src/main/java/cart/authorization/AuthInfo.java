package cart.authorization;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthInfo {

    private final String email;
    private final String password;

    public AuthInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
