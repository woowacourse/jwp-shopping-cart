package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import woowacourse.shoppingcart.service.dto.CustomerDeleteServiceRequest;

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

    public CustomerDeleteServiceRequest toServiceRequest() {
        return new CustomerDeleteServiceRequest(this.password);
    }
}
