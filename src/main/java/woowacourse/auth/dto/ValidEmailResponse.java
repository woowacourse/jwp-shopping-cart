package woowacourse.auth.dto;

public class ValidEmailResponse {
    private final Boolean isValidEmail;

    public ValidEmailResponse(Boolean isValidEmail) {
        this.isValidEmail = isValidEmail;
    }

    public Boolean getValidEmail() {
        return isValidEmail;
    }
}
