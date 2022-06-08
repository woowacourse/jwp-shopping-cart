package woowacourse.shoppingcart.entity;

import java.util.Objects;

public class OrderEntity {

    private final Long id;
    private final Long customerId;

    public OrderEntity(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    public OrderEntity(Long customerId) {
        this(null, customerId);
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId);
    }
}
