package cart.fixture;

import static cart.fixture.DomainFactory.MAC_BOOK;
import static cart.fixture.DomainFactory.MAC_BOOK_CART;

import cart.controller.dto.AuthInfo;
import cart.domain.cart.Cart;
import cart.domain.item.Item;
import cart.service.dto.CartDto;
import cart.service.dto.ItemDto;

public final class DtoFactory {

    public static final ItemDto MAC_BOOK_ITEM_DTO = createItemDto(MAC_BOOK);
    public static final CartDto MAC_BOOK_CART_DTO = createCartDto(MAC_BOOK_CART);

    public static ItemDto createItemDto(Item item) {
        return ItemDto.from(item);
    }

    public static CartDto createCartDto(Cart cart) {
        return CartDto.from(cart);
    }

    public static AuthInfo createAuthInfo() {
        return new AuthInfo("a@a.com", "a");
    }

    private DtoFactory() {
    }
}
