package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerUpdateRequest {

    @Size(min = 1, max = 10)
    @Pattern(regexp = "\\S*", message = "공백을 포함하면 안됩니다")
    private String username;

    private CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
