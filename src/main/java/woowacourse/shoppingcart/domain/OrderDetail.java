package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private Product product;
    private Quantity quantity;

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = new Quantity(quantity);
    }

    public int calculatePrice() {
        return product.getPrice() * getQuantity();
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }
}
