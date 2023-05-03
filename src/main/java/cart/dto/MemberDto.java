package cart.dto;

import cart.entity.Member;

public class MemberDto {
    private String email;
    private String password;

    private MemberDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberDto from(Member member) {
        return new MemberDto(member.getEmail(), member.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
