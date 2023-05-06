package cart.dto;

public class CartProductAddRequestDto {

    private Integer productId;

    public CartProductAddRequestDto() {
    }

    public CartProductAddRequestDto(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
