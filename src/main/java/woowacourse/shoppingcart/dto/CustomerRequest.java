package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerRequest {

    @NotNull
    @Size(min = 3, max = 10)
    private final String name;

    @NotNull
    @Size(min = 4, max = 20)
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

