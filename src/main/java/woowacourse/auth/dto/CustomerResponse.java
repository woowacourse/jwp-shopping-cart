package woowacourse.auth.dto;

import java.util.Objects;
import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private Long id;
    private String email;
    private String nickname;

    public CustomerResponse(Customer customer) {
        this(customer.getId(), customer.getEmail(), customer.getNickname());
    }

    public CustomerResponse(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerResponse)) {
            return false;
        }
        CustomerResponse that = (CustomerResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email)
                && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nickname);
    }
}
