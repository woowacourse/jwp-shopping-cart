package woowacourse.auth.dto;

public class LogInRequest {

    private String email;
    private String password;

    public LogInRequest() {
    }

    public LogInRequest(String email, String password) {
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
