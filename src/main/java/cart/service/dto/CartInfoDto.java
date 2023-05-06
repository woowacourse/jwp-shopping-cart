package cart.service.dto;

import cart.dao.dto.CartProductDto;

public class CartInfoDto {

    private long cartId;
    private String productName;
    private String imgUrl;
    private int price;

    public CartInfoDto(final long cartId, final String productName, final String imgUrl, final int price) {
        this.cartId = cartId;
        this.productName = productName;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static CartInfoDto from(final CartProductDto cartProductDto) {
        return new CartInfoDto(cartProductDto.getCartId(), cartProductDto.getProductName(), cartProductDto.getImgUrl(),
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
