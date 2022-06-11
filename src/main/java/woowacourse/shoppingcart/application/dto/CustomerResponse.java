package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Customer;

import java.util.Objects;

public class CustomerResponse {

    private Long id;
    private String email;
    private String nickname;

    public CustomerResponse() {
    }

    public CustomerResponse(final Customer customer) {
        this(customer.getId(), customer.getEmail(), customer.getNickname());
    }

    public CustomerResponse(final Long id, final String email, final String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
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
