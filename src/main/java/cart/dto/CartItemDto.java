package cart.dto;

public class CartItemDto {

    private Long cartId;
    private Long productId;
    private String name;
    private String imgUrl;
    private int price;

    public CartItemDto(Long cartId, Long productId, String name, String imgUrl, int price) {
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price;
    }
}
