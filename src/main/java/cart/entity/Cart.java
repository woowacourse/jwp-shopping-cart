package cart.entity;

import cart.vo.Email;

public class Cart {

    private final Long id;
    private final Email email;
    private final Long productId;

    public Cart(Long id, Email email, Long productId) {
        this.id = id;
        this.email = email;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public Long getProductId() {
        return productId;
    }

    public static class Builder {

        private Long id;
        private Email email;
        private Long productId;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Cart build() {
            return new Cart(id, email, productId);
        }

    }

    public boolean canUserWithThisEmailBeDeleted(Email email) {
        return this.email.equals(email);
    }

}
