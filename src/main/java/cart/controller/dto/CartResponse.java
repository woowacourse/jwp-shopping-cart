package cart.controller.dto;

import cart.service.dto.CartInfoDto;

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

    public static CartResponse fromDto(final CartInfoDto cartInfoDto) {
        return new CartResponse(cartInfoDto.getCartId(), cartInfoDto.getProductName(), cartInfoDto.getImgUrl(),
                cartInfoDto.getPrice());
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
