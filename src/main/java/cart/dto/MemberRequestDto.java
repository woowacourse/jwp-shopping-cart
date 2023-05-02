package cart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MemberRequestDto {
    @NotNull(message = "사용자 이메일은 비어있으면 안됩니다.")
    @Email(message = "이메일 형식을 준수해야합니다.")
    private String email;
    @NotNull(message = "사용자 패스워드는 비어있으면 안됩니다.")
    @Size(min = 1, max = 21, message = "3자이상 20자이하으로 작성해야 합니다.")
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
