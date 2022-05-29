package woowacourse.auth.dto;

public class SignInResponse {

    private String username;
    private String email;
    private String token;

    public SignInResponse(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
