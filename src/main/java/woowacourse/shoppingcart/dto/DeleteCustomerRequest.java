package woowacourse.shoppingcart.dto;

import woowacourse.auth.support.PasswordCheck;

public class DeleteCustomerRequest {

    @PasswordCheck
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
