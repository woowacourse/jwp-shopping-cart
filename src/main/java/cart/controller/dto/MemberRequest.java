package cart.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberRequest {

    @Email(message = "이메일 형식을 확인하세요.")
    private String email;

    @NotBlank(message = "빈 값은 입력될 수 없습니다.")
    private String password;

    @NotBlank(message = "빈 값은 입력될 수 없습니다.")
    private String name;

    private MemberRequest() {
    }

    public MemberRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
