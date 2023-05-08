package cart.web.settings.dto;

import cart.persistence.entity.Member;

public class MemberResponse {

    private final String email;
    private final String password;

    public MemberResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getEmail(), member.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
