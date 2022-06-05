package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmailDuplicationRequest {

    @Email
    @NotBlank
    private final String email;

    public EmailDuplicationRequest() {
        this(null);
    }

    public EmailDuplicationRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
