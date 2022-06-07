package woowacourse.shoppingcart.entity;

public class OrderDetailEntity {

    private final Long id;
    private final Long product_id;
    private final int quantity;

    public OrderDetailEntity(Long id, Long product_id, int quantity) {
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }
}
