package woowacourse.shoppingcart.dto;

public class EmailValidationRequest {

    private String email;

    private EmailValidationRequest() {
    }

    public EmailValidationRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
