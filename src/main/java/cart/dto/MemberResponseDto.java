package cart.dto;

import cart.entity.Member;

public class MemberResponseDto {

    private final String email;
    private final String password;

    public MemberResponseDto(Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
