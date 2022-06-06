package woowacourse.auth.dto;

public class ValidEmailResponse {
    private final Boolean validEmail;

    public ValidEmailResponse(Boolean isValidEmail) {
        this.validEmail = isValidEmail;
    }

    public Boolean getValidEmail() {
        return validEmail;
    }
}
