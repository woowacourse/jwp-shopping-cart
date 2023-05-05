package cart.dto;

public class CartDto {
    private Long memeberId;
    private Long productId;

    public CartDto(Long memeberId, Long productId) {
        this.memeberId = memeberId;
        this.productId = productId;
    }

    public Long getMemeberId() {
        return memeberId;
    }

    public Long getProductId() {
        return productId;
    }
}
