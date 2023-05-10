package cart.member.dto;

import lombok.Getter;

@Getter
public class MemberDto {

    private final String email;
    private final String password;
    private final String phoneNumber;


    public MemberDto(String email, String password, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
