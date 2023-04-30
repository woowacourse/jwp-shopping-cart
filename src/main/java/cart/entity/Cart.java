package cart.entity;

import java.util.Objects;

public class Cart {
    private final String memberEmail;
    private final Long productId;

    public Cart(String memberEmail, Long productId) {
        this.memberEmail = memberEmail;
        this.productId = productId;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(memberEmail, cart.memberEmail) && Objects.equals(productId, cart.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberEmail, productId);
    }
}
