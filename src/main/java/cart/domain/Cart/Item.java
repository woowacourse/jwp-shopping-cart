package cart.domain.Cart;

import java.util.Objects;

public class Item {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    public Item(Long memberId, Long productId) {
        this(null, memberId, productId);
    }

    public Item(Long id, Long memberId, Long productId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(memberId, item.memberId) && Objects.equals(productId, item.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, productId);
    }
}
