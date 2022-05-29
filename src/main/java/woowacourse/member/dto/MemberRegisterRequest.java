package woowacourse.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class MemberRegisterRequest {

    private static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!?-])[A-Za-z\\d@!?-]{6,20}";

    @NotBlank(message = "이메일에는 공백이 허용되지 않습니다.")
    @Email(message = "올바른 이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호에는 공백이 허용되지 않습니다.")
    @Pattern(regexp = REGEX, message = "올바른 비밀번호 형식으로 입력해주세요.")
    private String password;

    @NotBlank(message = "이름에는 공백이 허용되지 않습니다.")
    @Length(min = 1, max = 10, message = "이름은 1자 이상 10자 이하여야합니다.")
    private String name;

    private MemberRegisterRequest() {
    }

    public MemberRegisterRequest(String email, String password, String name) {
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
