package woowacourse.auth.dto;

import javax.validation.constraints.NotNull;

public class TokenRequest {

    @NotNull(message = "아이디 입력 필요")
    private String username;
    @NotNull(message = "비밀번호 입력 필요")
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
