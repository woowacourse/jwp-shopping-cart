package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class CustomerRequest {

    @NotNull
    private final String name;
    @NotNull
    private final String password;

    public CustomerRequest(String name, String password) {
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

