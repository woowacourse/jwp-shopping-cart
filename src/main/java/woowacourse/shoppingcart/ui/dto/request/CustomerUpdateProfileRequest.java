package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.service.dto.CustomerUpdateProfileServiceRequest;

public class CustomerUpdateProfileRequest {
    @NotBlank
    private String name;

    public CustomerUpdateProfileRequest() {
    }

    public CustomerUpdateProfileRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public CustomerUpdateProfileServiceRequest toServiceRequest() {
        return new CustomerUpdateProfileServiceRequest(this.name);
    }
}
