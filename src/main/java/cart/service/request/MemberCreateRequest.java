package cart.service.request;

import javax.validation.constraints.NotBlank;

public class MemberCreateRequest {
    @NotBlank(message = "회원 이름은 null 또는 공백일 수 없습니다.")
    private String name;

    @NotBlank(message = "회원 이메일은 null 또는 공백일 수 없습니다.")
    private String email;

    @NotBlank(message = "회원 패스워드는 null 또는 공백일 수 없습니다.")
    private String password;

    public MemberCreateRequest() {
    }

    public MemberCreateRequest(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
