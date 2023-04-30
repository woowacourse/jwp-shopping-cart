package cart.fixture;

import cart.controller.dto.request.AddItemRequest;
import cart.controller.dto.request.UpdateItemRequest;

public final class RequestFactory {

    public static final AddItemRequest ADD_MAC_BOOK_REQUEST =
            createAddItemRequest("맥북", "http://image.url", 1_500_000);
    public static final UpdateItemRequest UPDATE_MAC_BOOK_REQUEST =
            createUpdateItemRequest("맥북", "http://image.url", 1_500_000);

    public static UpdateItemRequest createUpdateItemRequest(String name, String imageUrl, int price) {
        return new UpdateItemRequest(name, imageUrl, price);
    }

    public static AddItemRequest createAddItemRequest(String name, String imageUrl, int price) {
        return new AddItemRequest(name, imageUrl, price);
    }

    private RequestFactory() {
    }
}
