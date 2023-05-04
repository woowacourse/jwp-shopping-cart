package cart.domain;


public class CartProduct {

    private final Long id;
    private final Product product;

    public CartProduct(Long id, Product product) {
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
