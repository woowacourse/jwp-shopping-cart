package woowacourse.shoppingcart.dto.request;

import org.hibernate.validator.constraints.Length;
import woowacourse.auth.support.PasswordCheck;

public class DeleteCustomerRequest {

    @PasswordCheck
    @Length(min = 6)
    private String password;

    public DeleteCustomerRequest() {
    }

    public DeleteCustomerRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
