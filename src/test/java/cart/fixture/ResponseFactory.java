package cart.fixture;

import static cart.fixture.DomainFactory.MAC_BOOK;

import cart.controller.dto.response.ItemResponse;
import cart.domain.item.Item;
import cart.service.dto.ItemDto;

public final class ResponseFactory {

    public static final ItemResponse MAC_BOOK_RESPONSE =
            createItemResponse(MAC_BOOK.getId(), MAC_BOOK.getName(), MAC_BOOK.getImageUrl(), MAC_BOOK.getPrice());

    public static ItemResponse createItemResponse(Long id, String name, String imageUrl, int price) {
        return ItemResponse.from(ItemDto.from(new Item(id, name, imageUrl, price)));
    }

    private ResponseFactory() {
    }
}
