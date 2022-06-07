package woowacourse.shoppingcart.domain;

public class Cart {
    private final Long id;
    private final int quantity;
    private final Product product;

    public Cart(Long id, int quantity, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
