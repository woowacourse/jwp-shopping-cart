package cart.auth;

import cart.domain.Member;
import cart.dto.MemberDto;

public class AuthDto {

    private String email;
    private String password;

    public AuthDto() {
    }

    public AuthDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public Member toMember() {
        return new Member(email, password);
    }

    public MemberDto toMemberDto() {
        return new MemberDto(email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
