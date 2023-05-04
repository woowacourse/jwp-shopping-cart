package cart.dto.response;

import cart.dto.CartItemDetailsDto;

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

    public static CartItemResponse from(final CartItemDetailsDto cartItemDetailsDto) {
        return new CartItemResponse(cartItemDetailsDto.getId(), cartItemDetailsDto.getName(), cartItemDetailsDto.getImage(), cartItemDetailsDto.getPrice());
    }

    public static List<CartItemResponse> from(final List<CartItemDetailsDto> cartItemDetailsDtos) {
        return cartItemDetailsDtos.stream()
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
