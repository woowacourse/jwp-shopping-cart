package cart.dto.response;

import cart.domain.member.dto.MemberDto;

public class MemberResponse {

    private String email;
    private String password;

    public MemberResponse() {
    }

    public MemberResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberResponse of(final MemberDto memberDto) {
        return new MemberResponse(memberDto.getEmail(), memberDto.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
