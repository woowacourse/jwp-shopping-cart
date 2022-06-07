package woowacourse.shoppingcart.entity;

public class CartEntity {

    private Long id;
    private Long customer_id;
    private Long product_id;
    private Long quantity;
    private boolean checked;

    public CartEntity(Long id, Long customer_id, Long product_id, Long quantity, boolean checked) {
        this.id = id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}
