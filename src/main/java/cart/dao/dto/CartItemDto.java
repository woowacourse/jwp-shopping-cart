package cart.dao.dto;

public class CartItemDto {

    private final Integer userId;
    private final Integer productId;

    public CartItemDto(Integer userId, Integer productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProductId() {
        return productId;
    }

}
