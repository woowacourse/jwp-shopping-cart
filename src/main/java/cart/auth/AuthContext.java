package cart.auth;

import cart.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthContext {

    private Member authMember;

    public void setAuthMember(final Member authMember) {
        this.authMember = authMember;
    }

    public Member getAuthMember() {
        return authMember;
    }
}
