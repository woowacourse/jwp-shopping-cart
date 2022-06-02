package woowacourse.auth.dto;

public class ValidEmailRequest {

    private final String email;

    public ValidEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
