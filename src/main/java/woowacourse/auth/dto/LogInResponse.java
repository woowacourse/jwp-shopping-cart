package woowacourse.auth.dto;

public class LogInResponse {

    private String username;
    private String email;
    private String token;

    public LogInResponse(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }

    private LogInResponse() {
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
