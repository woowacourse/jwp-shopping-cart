package woowacourse.shoppingcart.domain;

public class CartItem {

    private Long id;
    private Product product;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
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
