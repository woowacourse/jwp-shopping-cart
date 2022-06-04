package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;

public class EmailRequest {

    @Email(message = "올바른 이메일을 입력해주세요.")
    private final String email;

    public EmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
