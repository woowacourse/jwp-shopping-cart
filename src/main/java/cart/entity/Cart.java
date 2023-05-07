package cart.entity;

import cart.entity.vo.Email;

public class Cart {

    private final Long id;
    private final Email userEmail;
    private final long productId;

    public Cart(final Long id, final Email userEmail, final long productId) {
        this.id = id;
        this.userEmail = userEmail;
        this.productId = productId;
    }

    public boolean isCartOwner(final Email email) {
        return userEmail.equals(email);
    }

    public Long getId() {
        return id;
    }

    public Email getUserEmail() {
        return userEmail;
    }

    public long getProductId() {
        return productId;
    }
}
