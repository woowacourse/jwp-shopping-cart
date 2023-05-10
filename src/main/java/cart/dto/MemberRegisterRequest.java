package cart.dto;

import static cart.validation.ValidationGroups.PatternCheckGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import cart.validation.ValidationGroups.NotBlankGroup;

public class MemberRegisterRequest {

    @NotBlank(message = "닉네임은 필수입니다.", groups = NotBlankGroup.class)
    private String nickname;

    @NotBlank(message = "이메일은 필수입니다.", groups = NotBlankGroup.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]+$", message = "이메일 형식에 맞지 않습니다. 이메일 형식 : aa@bb.cc", groups = PatternCheckGroup.class)
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.", groups = NotBlankGroup.class)
    private String password;

    private MemberRegisterRequest() {
    }

    public MemberRegisterRequest(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
