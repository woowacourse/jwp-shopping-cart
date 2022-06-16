package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerRequest {

    @NotNull
    @Size(min = 5, max = 20)
    private final String userName;

    @NotNull
    @Size(min = 8, max = 16)
    private final String password;

    public CustomerRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}

