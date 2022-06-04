package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class CustomerDeleteRequest {

    @NotBlank
    @Length(min = 8)
    private String password;

    public CustomerDeleteRequest() {
    }

    public CustomerDeleteRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
