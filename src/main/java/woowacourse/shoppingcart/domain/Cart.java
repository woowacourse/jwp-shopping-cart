package woowacourse.shoppingcart.domain;

public class Cart {

    private Long id;
    private Product product;
    private Integer quantity;

    public Cart(Long id, Product product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Cart(Product product, Integer quantity) {
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
}
