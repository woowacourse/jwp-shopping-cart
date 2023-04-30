package cart.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDto {
    private final Long id;

    @NotBlank(message = "이메일에 빈 값을 입력할 수 없습니다.")
    @Email(message = "이메일 형식에 맞게 입력해 주세요.")
    private final String email;

    @NotBlank(message = "사용자 비밀번호는 비어있을 수 없습니다.")
    private final String password;

    @NotNull(message = "사용자 닉네임은 비어있을 수 없습니다.")
    private final String nickname;

    @Pattern(regexp = "010-\\d{4}-\\d{4}", message = "올바른 전화번호 형식을 입력해 주세요.")
    private final String telephone;

    public UserDto(final Long id, final String email, final String password, final String nickname, final String telephone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTelephone() {
        return telephone;
    }
}
