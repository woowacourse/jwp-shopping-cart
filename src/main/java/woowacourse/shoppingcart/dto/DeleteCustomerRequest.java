package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DeleteCustomerRequest {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{6,}$")
    @Size(min = 6)
    private String password;

    private DeleteCustomerRequest() {
    }

    public DeleteCustomerRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
