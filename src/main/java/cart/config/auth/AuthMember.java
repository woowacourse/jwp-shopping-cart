package cart.config.auth;

import cart.domain.member.MemberId;

public class AuthMember {
    private final MemberId id;
    private final String email;

    public AuthMember(final MemberId id, final String email) {
        this.id = id;
        this.email = email;
    }

    public MemberId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
