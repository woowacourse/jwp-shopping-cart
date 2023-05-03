package cart.entiy;

public class CartEntity {

    private final String email;
    private final Long productId;

    public CartEntity(final String email, final Long productId) {
        this.email = email;
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public Long getProductId() {
        return productId;
    }
}
