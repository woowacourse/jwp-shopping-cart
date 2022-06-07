package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class MemberCreateRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$",
            message = "비밀번호 형식이 올바르지 않습니다.")
    private String password;
    @Pattern(regexp = "^[가-힣]{1,5}$", message = "닉네임 형식이 올바르지 않습니다.")
    private String nickname;

    private MemberCreateRequest() {
    }

    public MemberCreateRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
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
}
