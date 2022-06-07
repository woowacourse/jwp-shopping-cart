package woowacourse.shoppingcart.dto.customer;

import java.util.Objects;

public class LoginCustomer {

    private final String username;

    public LoginCustomer(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LoginCustomer that = (LoginCustomer)o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
