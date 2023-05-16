package cart.entity;

import cart.exception.DomainException;
import cart.exception.ExceptionCode;

public class CartItem {
    private final Long id;
    private final Long memberId;
    private final Long productId;

    private CartItem(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = validateId(memberId);
        this.productId = validateId(productId);
    }

    public static CartItem of(Long id, Long memberId, Long productId) {
        return new CartItem(id, memberId, productId);
    }

    private Long validateId(Long id) {
        if (id < 1L) {
            throw new DomainException(ExceptionCode.INVALID_ID);
        }
        return id;
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
