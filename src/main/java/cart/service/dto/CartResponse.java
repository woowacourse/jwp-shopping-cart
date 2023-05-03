package cart.service.dto;

import cart.dao.dto.CartProductDto;

public class CartResponse {

    private long cartId;
    private String productName;
    private String imgUrl;
    private int price;

    public CartResponse(final long cartId, final String productName, final String imgUrl, final int price) {
        this.cartId = cartId;
        this.productName = productName;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public CartResponse() {
    }

    public static CartResponse from(final CartProductDto cartProductDto) {
        return new CartResponse(cartProductDto.getCartId(), cartProductDto.getProductName(), cartProductDto.getImgUrl(),
                cartProductDto.getPrice());
    }

    public long getCartId() {
        return cartId;
    }

    public String getProductName() {
        return productName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price;
    }
}
