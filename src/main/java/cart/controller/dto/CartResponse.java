package cart.controller.dto;

import java.util.List;

public class CartResponse {

    private final Long memberId;
    private final List<ItemResponse> itemResponses;

    public CartResponse(Long memberId, List<ItemResponse> itemResponses) {
        this.memberId = memberId;
        this.itemResponses = itemResponses;
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<ItemResponse> getItemResponses() {
        return itemResponses;
    }
}
