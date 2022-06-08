package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class CustomerRequest {

    @NotNull
    private final String userName;
    @NotNull
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

