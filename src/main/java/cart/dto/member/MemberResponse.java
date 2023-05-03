package cart.dto.member;

import cart.entity.member.Member;

public class MemberResponse {

    private final String email;
    private final String password;

    public MemberResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public MemberResponse(final Member member) {
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
