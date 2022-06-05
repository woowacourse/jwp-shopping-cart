package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private final int quantity;
    private final Product product;

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
