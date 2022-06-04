package woowacourse.shoppingcart.dto;

public class EmailResponse {
    private final Boolean isValidEmail;

    public EmailResponse(Boolean isValidEmail) {
        this.isValidEmail = isValidEmail;
    }

    public Boolean getIsValidEmail() {
        return isValidEmail;
    }
}
