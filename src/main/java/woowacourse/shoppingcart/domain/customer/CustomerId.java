package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class CustomerId {
    private final Long id;

    public CustomerId(final Long id) {
        this.id = id;
    }

    public Long getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerId customerId1 = (CustomerId) o;
        return Objects.equals(id, customerId1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
