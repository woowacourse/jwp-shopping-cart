package cart.dto;

public class CartRequestDto {
    private int productId;

    public CartRequestDto(){}

    public CartRequestDto(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

}
