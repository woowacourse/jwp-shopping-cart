package woowacourse.member.application.dto.request;

public class LoginServiceRequest {

    private final String email;
    private final String password;

    public LoginServiceRequest(String email, String password) {
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
