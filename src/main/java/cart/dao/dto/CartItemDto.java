package cart.dao.dto;

public class CartItemDto {

    private final Integer id;
    private final Integer userId;
    private final Integer productId;

    public CartItemDto(Integer id, Integer userId, Integer productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProductId() {
        return productId;
    }

}
