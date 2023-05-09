package cart.dto;

import cart.entity.Member;

public class MemberResponseDto {

    private String email;
    private String password;

    public MemberResponseDto(){
    }

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
