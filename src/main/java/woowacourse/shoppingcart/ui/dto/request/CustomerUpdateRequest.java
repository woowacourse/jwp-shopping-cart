package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotBlank;

public class CustomerUpdateRequest {
    @NotBlank
    private final String name;
    @NotBlank
    private final String password;

    public CustomerUpdateRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
