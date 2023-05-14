package cart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

public class MemberRequest {

    @Nullable
    private final String name;
    @NotBlank (message = "회원의 이메일은 비어있을 수 없습니다.")
    @Email
    private final String email;
    @NotBlank (message = "회원의 비밀번호는 비어있을 수 없습니다.")
    @Length(min = 4, max = 16)
    private final String password;

    public MemberRequest(String name, String email, String password) {
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
