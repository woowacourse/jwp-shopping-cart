package cart.dto.cart;

import java.util.Objects;

public class CartItemResponseDto {
    private final Long id;
    private final String name;
    private final String imgUrl;
    private final int price;

    private CartItemResponseDto(Long id, String name, String imgUrl, int price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static CartItemResponseDto of(Long id, String name, String imgUrl, int price) {
        return new CartItemResponseDto(id, name, imgUrl, price);
    }

    public static CartItemResponseDto fromDto(CartItemDto cartItemDto) {
        return new CartItemResponseDto(cartItemDto.getId(), cartItemDto.getName(), cartItemDto.getImgUrl(), cartItemDto.getPrice());
    }

    public Long getId() {
        return id;
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
        return Objects.hash(id, name, imgUrl, price);
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
        return price == that.price && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(imgUrl, that.imgUrl);
    }
}
