package woowacourse.auth.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TokenRequest {

    @Pattern(regexp = "^[a-z0-9_-]{5,20}$",
            message = "유저 네임 형식이 올바르지 않습니다. (영문 소문자, 숫자와 특수기호(_), (-)만 사용 가능)")
    private String username;

    @NotEmpty(message = "비밀번호 필수 입력 사항압니다.")
    @Size(min = 8, max = 16, message = "비밀번호 길이가 올바르지 않습니다. (길이: 8 이상 16 이하)")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,16}$", message = "비밀번호 형식이 올바르지 않습니다. (영문자, 숫자, 특수문자!, @, #, $, %, ^, &, *, (, )를 모두 사용)")
    private String password;

    private TokenRequest() {
    }

    public TokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
