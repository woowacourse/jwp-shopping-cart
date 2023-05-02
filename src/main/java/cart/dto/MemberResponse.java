package cart.dto;

import cart.domain.member.entity.Member;

public class MemberResponse {

    private String email;
    private String password;

    public MemberResponse() {
    }

    public MemberResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberResponse of(final Member member) {
        return new MemberResponse(member.getEmail(), member.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
