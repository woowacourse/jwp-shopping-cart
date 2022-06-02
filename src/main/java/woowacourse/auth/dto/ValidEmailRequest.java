package woowacourse.auth.dto;

public class ValidEmailRequest {

    private String email;

    public ValidEmailRequest() {
    }

    public ValidEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
