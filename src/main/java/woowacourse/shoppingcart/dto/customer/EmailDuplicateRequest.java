package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.Pattern;

public class EmailDuplicateRequest {

    @Pattern(regexp = "^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$",
            message = "이메일 형식이 올바르지 않습니다. (형식: example@email.com)")
    private final String email;

    public EmailDuplicateRequest() {
        this(null);
    }

    public EmailDuplicateRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
