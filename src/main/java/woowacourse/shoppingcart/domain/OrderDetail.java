package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private final Long id;
    private final Product product;
    private final Integer quantity;

    public OrderDetail(Long id, Product product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderDetail(Product product, Integer quantity) {
        this(null, product, quantity);
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
