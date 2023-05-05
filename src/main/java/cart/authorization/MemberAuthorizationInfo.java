package cart.authorization;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberAuthorizationInfo {

    private final String email;
    private final String password;

    public MemberAuthorizationInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
