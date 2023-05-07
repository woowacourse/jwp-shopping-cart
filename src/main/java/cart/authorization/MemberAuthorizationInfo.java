package cart.authorization;

import lombok.Getter;

@Getter
public class MemberAuthorizationInfo {

    private final String email;
    private final String password;

    public MemberAuthorizationInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
