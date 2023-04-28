package cart.fixture;

import cart.controller.dto.ItemResponse;
import cart.domain.item.Item;
import cart.service.dto.ItemDto;

public final class ResponseFactory {

    public static final ItemResponse MAC_BOOK_RESPONSE =
            createItemResponse(1L, "맥북", "http://image.url", 1_500_000);

    public static ItemResponse createItemResponse(Long id, String name, String imageUrl, int price) {
        return ItemResponse.from(new ItemDto(new Item(id, name, imageUrl, price)));
    }

    private ResponseFactory() {
    }
}
