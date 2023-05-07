package cart.auth.credential;

import cart.domain.Id;
import cart.domain.member.MemberEmail;
import cart.domain.member.MemberPassword;

public class Credential {

    private final Id memberId;
    private final MemberEmail email;
    private final MemberPassword password;

    public Credential(Id memberId, MemberEmail email, MemberPassword password) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
    }

    public Id getMemberId() {
        return memberId;
    }

    public MemberEmail getEmail() {
        return email;
    }

    public MemberPassword getPassword() {
        return password;
    }
}
