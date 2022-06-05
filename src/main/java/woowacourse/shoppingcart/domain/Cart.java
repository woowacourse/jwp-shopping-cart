package woowacourse.shoppingcart.domain;

public class Cart {

    private final Long id;
    private final Product product;

    public Cart(Long id, Product product) {
        this.id = id;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }
}
