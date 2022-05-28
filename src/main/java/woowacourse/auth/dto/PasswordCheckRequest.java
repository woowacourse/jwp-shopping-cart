package woowacourse.auth.dto;

public class PasswordCheckRequest {

    private String password;

    public PasswordCheckRequest() {
    }

    public PasswordCheckRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
