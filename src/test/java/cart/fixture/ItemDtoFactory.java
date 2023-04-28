package cart.fixture;

import static cart.fixture.ResponseFactory.MAC_BOOK_RESPONSE;

import cart.domain.item.Item;
import cart.service.dto.ItemDto;

public final class ItemDtoFactory {

    public static ItemDto createItemDto() {
        return new ItemDto(new Item(MAC_BOOK_RESPONSE.getId(), MAC_BOOK_RESPONSE.getName(),
                MAC_BOOK_RESPONSE.getImageUrl(), MAC_BOOK_RESPONSE.getPrice()));
    }

    private ItemDtoFactory() {
    }
}
