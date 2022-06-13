package woowacourse.auth.dto;

import javax.validation.constraints.Pattern;

public class TokenRequest {

    @Pattern(regexp = "^[a-z0-9_-]{5,20}$",
            message = "유저 네임 형식이 올바르지 않습니다. (영문 소문자, 숫자와 특수기호(_), (-)만 사용 가능, 5자 이상 20자 이내)")
    private final String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,16}$",
            message = "비밀번호 형식이 올바르지 않습니다. (영문자, 숫자, 특수문자!, @, #, $, %, ^, &, *, (, )를 모두 사용, 8자 이상 16자 이내)")
    private final String password;

    private TokenRequest() {
        this(null, null);
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
