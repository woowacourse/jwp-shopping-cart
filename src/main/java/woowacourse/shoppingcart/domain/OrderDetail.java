package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private final int quantity;
    private final Product product;

    public OrderDetail(final int quantity, final Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
