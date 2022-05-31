package woowacourse.auth.application.dto;

public class TokenCreateRequest {

    private String email;
    private String password;

    public TokenCreateRequest(String email, String password) {
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
