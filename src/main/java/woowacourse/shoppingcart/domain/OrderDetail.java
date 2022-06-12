package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private final Product product;
    private final Quantity quantity;

    public OrderDetail(Product product, Quantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return product.getId();
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}
