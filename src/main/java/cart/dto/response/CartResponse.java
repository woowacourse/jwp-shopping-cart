package cart.dto.response;

import java.util.List;

public class CartResponse {

    private final Long userId;
    private final List<ProductResponse> items;

    public CartResponse(Long userId, List<ProductResponse> items) {
        this.userId = userId;
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public List<ProductResponse> getItems() {
        return items;
    }
}
