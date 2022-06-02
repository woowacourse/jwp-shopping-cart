package woowacourse.auth.dto.request;

public class PasswordCheckRequest {

    private String password;

    private PasswordCheckRequest() {
    }

    public PasswordCheckRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
