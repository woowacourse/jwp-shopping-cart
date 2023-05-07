package cart.dto.response;

import java.util.List;

public class CartResponse {

    private final Long userId;
    private final List<ItemResponse> items;

    public CartResponse(Long userId, List<ItemResponse> items) {
        this.userId = userId;
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public List<ItemResponse> getItems() {
        return items;
    }
}
