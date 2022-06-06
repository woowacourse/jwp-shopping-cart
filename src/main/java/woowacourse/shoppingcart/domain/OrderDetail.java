package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private Product product;
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
