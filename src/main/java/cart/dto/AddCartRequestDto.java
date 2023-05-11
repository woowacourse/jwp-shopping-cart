package cart.dto;

public class AddCartRequestDto {

    private Long productId;

    public AddCartRequestDto() {
    }

    public AddCartRequestDto(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
