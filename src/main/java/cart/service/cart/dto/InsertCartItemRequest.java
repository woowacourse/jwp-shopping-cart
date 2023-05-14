package cart.service.cart.dto;

public class InsertCartItemRequest {
    private final String email;
    private final Long productId;

    public InsertCartItemRequest(String email, Long productId) {
        this.email = email;
        this.productId = productId;
    }

    public InsertCartItemRequest(String email) {
        this(email, null);
    }

    public String getEmail() {
        return email;
    }

    public Long getProductId() {
        return productId;
    }
}
