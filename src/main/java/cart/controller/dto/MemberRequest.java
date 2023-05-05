package cart.controller.dto;

import javax.validation.constraints.NotBlank;

public class MemberRequest {

    @NotBlank(message = "이름은 공백이어서는 안됩니다.")
    private String email;

    @NotBlank(message = "비밀번호는 공백이어서는 안됩니다.")
    private String password;

    public MemberRequest() {
    }

    public MemberRequest(final String email, final String password) {
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
