package woowacourse.auth.dto.request;

public class PasswordUpdateRequest {

    private String password;

    public PasswordUpdateRequest() {
    }

    public PasswordUpdateRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
