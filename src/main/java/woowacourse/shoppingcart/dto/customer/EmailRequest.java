package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.Email;

public class EmailRequest {

    @Email(message = "올바른 이메일을 입력해주세요.")
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
