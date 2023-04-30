package cart.entity;

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
}
