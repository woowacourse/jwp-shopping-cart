package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

import java.util.Objects;

public class CreateCustomerRequest {
    private final String email;
    private final String nickname;
    private final String password;

    public CreateCustomerRequest(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public Customer toCustomer() {
        return new Customer(email, nickname, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateCustomerRequest that = (CreateCustomerRequest) o;
        return Objects.equals(email, that.email) && Objects.equals(nickname, that.nickname) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nickname, password);
    }
}
