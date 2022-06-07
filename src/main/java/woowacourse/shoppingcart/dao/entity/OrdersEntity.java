package woowacourse.shoppingcart.dao.entity;

public class OrdersEntity {

    private final Long id;
    private final Long customerId;

    public OrdersEntity(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
