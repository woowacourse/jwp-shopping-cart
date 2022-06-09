package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class DeleteCustomerRequest {

    @NotBlank
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
