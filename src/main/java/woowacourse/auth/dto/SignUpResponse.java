package woowacourse.auth.dto;

public class SignUpResponse {

    private String username;
    private String email;

    public SignUpResponse() {
    }

    public SignUpResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
