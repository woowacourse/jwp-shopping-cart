package cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public final class MemberAuthDto {

    @Schema(description = "멤버 이메일")
    private String email;
    @Schema(description = "멤버 비밀번호")
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
