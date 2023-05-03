package cart.entity;

import cart.entity.vo.Email;

public class CartAddedProduct {

    private final Long id;
    private final Email userEmail;
    private final Product product;

    public CartAddedProduct(final Long id, final Email userEmail, final Product product) {
        this.id = id;
        this.userEmail = userEmail;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Email getUserEmail() {
        return userEmail;
    }

    public Product getProduct() {
        return product;
    }
}
