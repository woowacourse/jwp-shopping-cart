package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FindCustomerRequest {

    @NotBlank
    @Size(min = 5, max = 20)
    private final String name;

    public FindCustomerRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
