package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.Objects;

public class Orders {

    private Long id;
    private Long memberId;
    private List<OrderDetail> orderDetails;

    private Orders() {
    }

    public Orders(final Long id, final Long memberId) {
        this(id, memberId, null);
    }

    public Orders(final Long id, final Long memberId, final List<OrderDetail> orderDetails) {
        this.id = id;
        this.memberId = memberId;
        this.orderDetails = orderDetails;
    }

    public void lazyInitializeDetails(final List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Orders orders = (Orders) o;
        return Objects.equals(id, orders.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
