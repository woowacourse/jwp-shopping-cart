package cart.controller.dto;

import cart.dao.entity.CartEntity;
import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    public static List<CartResponse> from(List<CartEntity> entities) {
        return entities.stream().map(
            entity -> new CartResponse(entity.getId(), entity.getMember_id(),
                entity.getProduct_id())).collect(Collectors.toList());
    }

    public CartResponse(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

}
