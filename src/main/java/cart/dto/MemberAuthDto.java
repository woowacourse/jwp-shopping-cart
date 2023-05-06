package cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class MemberAuthDto {

    @Schema(description = "멤버 이메일")
    @NotBlank(message = "이메일은 필수로 존재해야 합니다.")
    @Size(min = 1, max = 255, message = "이메일 길이는 {min}보다 크고 {max}보다 작아야 합니다.")
    private String email;

    @Schema(description = "멤버 비밀번호")
    @NotBlank(message = "비밀번호는 필수로 존재해야 합니다.")
    @Size(min = 1, max = 50, message = "비밀번호 길이는 {min}보다 크고 {max}보다 작아야 합니다.")
    private String password;

    public MemberAuthDto(String email, String password) {
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
