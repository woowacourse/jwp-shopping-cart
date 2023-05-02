package cart.dto;

public class CartRequestDto {
    private final String email;
    private final Long productId;

    public CartRequestDto(String email, Long productId) {
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
