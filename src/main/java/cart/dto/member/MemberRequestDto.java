package cart.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberRequestDto {

    @Email
    private String email;
    @NotBlank
    private String password;

    public MemberRequestDto() {
    }

    public MemberRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
