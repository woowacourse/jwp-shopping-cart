package cart.dto.cart;

import cart.dto.product.ProductDto;

import java.util.Objects;

public class CartItemResponseDto {

    private final Long cartId;
    private final Long productId;
    private final String name;
    private final String imgUrl;
    private final int price;

    private CartItemResponseDto(Long cartId, Long productId, String name, String imgUrl, int price) {
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static CartItemResponseDto fromDto(CartItemDto cartItemDto) {
        ProductDto productDto = cartItemDto.getProductDto();
        return new CartItemResponseDto(cartItemDto.getId(), productDto.getId(), productDto.getName(), productDto.getImgUrl(), productDto.getPrice());
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

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId, name, imgUrl, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItemResponseDto that = (CartItemResponseDto) o;
        return price == that.price && Objects.equals(cartId, that.cartId) && Objects.equals(productId, that.productId) && Objects.equals(name, that.name) && Objects.equals(imgUrl, that.imgUrl);
    }

    @Override
    public String toString() {
        return "CartItemResponseDto{" +
                "cartId=" + cartId +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                '}';
    }
}
