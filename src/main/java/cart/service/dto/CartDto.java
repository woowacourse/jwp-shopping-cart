package cart.service.dto;

import cart.domain.cart.Cart;
import java.util.List;
import java.util.stream.Collectors;

public class CartDto {

    private final Long cartId;
    private final List<ItemDto> itemDtos;

    private CartDto(final Long cartId, final List<ItemDto> itemDtos) {
        this.cartId = cartId;
        this.itemDtos = itemDtos;
    }

    public static CartDto from(Cart cart) {
        List<ItemDto> itemDtos = cart.getItems()
                .stream()
                .map(ItemDto::from)
                .collect(Collectors.toList());

        return new CartDto(cart.getId(), itemDtos);
    }

    public Long getCartId() {
        return cartId;
    }

    public List<ItemDto> getItemDtos() {
        return itemDtos;
    }
}
