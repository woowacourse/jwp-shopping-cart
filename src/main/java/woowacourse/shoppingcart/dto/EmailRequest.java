package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class EmailRequest {

    @NotBlank
    private String email;

    public EmailRequest() {
    }

    public EmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
