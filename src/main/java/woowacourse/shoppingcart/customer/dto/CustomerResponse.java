package woowacourse.shoppingcart.customer.dto;

import java.util.Objects;
import woowacourse.shoppingcart.customer.domain.Customer;

public class CustomerResponse {

    private final String email;
    private final String nickname;

    private CustomerResponse(final String email, final String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public static CustomerResponse from(final Customer customer) {
        return new CustomerResponse(customer.getEmail(), customer.getNickname());
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerResponse that = (CustomerResponse) o;
        return Objects.equals(email, that.email) && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nickname);
    }
}
