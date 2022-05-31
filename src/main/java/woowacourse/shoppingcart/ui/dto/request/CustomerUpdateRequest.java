package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotBlank;

public class CustomerUpdateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    public CustomerUpdateRequest() {
    }

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
