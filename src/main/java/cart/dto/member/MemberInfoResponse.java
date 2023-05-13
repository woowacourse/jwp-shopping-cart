package cart.dto.member;

import cart.entity.member.Member;

public class MemberInfoResponse {

    private final String email;
    private final String password;

    public MemberInfoResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public MemberInfoResponse(final Member member) {
        this(
            member.getEmail(),
            member.getPassword()
        );
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
