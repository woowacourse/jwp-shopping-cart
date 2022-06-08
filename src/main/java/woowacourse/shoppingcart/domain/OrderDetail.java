package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private final Product product;
    private final Integer quantity;

    public OrderDetail(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderDetailProduct{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
