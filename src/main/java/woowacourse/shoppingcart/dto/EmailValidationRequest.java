package woowacourse.shoppingcart.dto;

public class EmailValidationRequest {
    private String email;

    public EmailValidationRequest() {
    }

    public EmailValidationRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
