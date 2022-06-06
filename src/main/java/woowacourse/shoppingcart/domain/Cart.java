package woowacourse.shoppingcart.domain;

public class Cart {

    private Long id;
    private Product product;
    private int quantity;

    public Cart() {
    }

    public Cart(Long id, Product product, int quantity) {
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
