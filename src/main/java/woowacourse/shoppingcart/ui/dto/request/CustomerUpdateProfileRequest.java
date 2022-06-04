package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotBlank;

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
}
