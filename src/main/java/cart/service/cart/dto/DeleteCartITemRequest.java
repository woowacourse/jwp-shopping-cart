package cart.service.cart.dto;

public class DeleteCartITemRequest {
    private final String email;
    private final long productId;

    public DeleteCartITemRequest(String email, long productId) {
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
