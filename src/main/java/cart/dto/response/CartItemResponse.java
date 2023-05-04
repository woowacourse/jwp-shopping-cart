package cart.dto.response;

import cart.dto.CartItemDto;

import java.util.List;
import java.util.stream.Collectors;


public class CartItemResponse {

    private final long id;
    private final String name;
    private final String image;
    private final int price;

    public CartItemResponse(final long id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static CartItemResponse from(final CartItemDto cartItemDto) {
        return new CartItemResponse(cartItemDto.getId(), cartItemDto.getName(), cartItemDto.getImage(), cartItemDto.getPrice());
    }

    public static List<CartItemResponse> from(final List<CartItemDto> cartItemDtos) {
        return cartItemDtos.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
