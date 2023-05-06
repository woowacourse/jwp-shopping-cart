package cart.service.cart.dto;

public class CartServiceRequest {
    private final String email;
    private final Long productId;

    public CartServiceRequest(String email, Long productId) {
        this.email = email;
        this.productId = productId;
    }

    public CartServiceRequest(String email) {
        this(email, null);
    }

    public String getEmail() {
        return email;
    }

    public Long getProductId() {
        return productId;
    }
}
