package cart.entity;

import cart.vo.Email;

public class Cart {

    private final Email email;
    private final Long productId;

    public Cart(Email email, Long productId) {
        this.email = email;
        this.productId = productId;
    }

    public String getEmail() {
        return email.getValue();
    }

    public Long getProductId() {
        return productId;
    }

    public static class Builder {

        private Email email;
        private Long productId;

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Cart build() {
            return new Cart(email, productId);
        }

    }

}
