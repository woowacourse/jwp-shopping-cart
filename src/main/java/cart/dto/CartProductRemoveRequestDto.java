package cart.dto;

public class CartProductRemoveRequestDto {

    private Integer productId;

    public CartProductRemoveRequestDto() {
    }

    public CartProductRemoveRequestDto(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
