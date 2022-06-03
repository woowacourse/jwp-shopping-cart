package woowacourse.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TokenRequest {
    @NotBlank(message = "유저 이름의 길이는 5이상 20이하여야 합니다.")
    @Size(min = 5, max = 20, message = "유저 이름의 길이는 5이상 20이하여야 합니다.")
    private String userName;

    @NotBlank(message = "비밀번호의 길이는 8이상 16이하여야 합니다.")
    @Size(min = 8, max = 16, message = "비밀번호의 길이는 8이상 16이하여야 합니다.")
    private String password;

    private TokenRequest() {
    }

    public TokenRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
