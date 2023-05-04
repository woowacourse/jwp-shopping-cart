package cart.entity;

import java.util.Objects;

public class Cart {

    private final Long id;
    private final Long memberId;
    private final Long itemId;

    public Cart(Long id, Long memberId, Long itemId) {
        this.id = id;
        this.memberId = memberId;
        this.itemId = itemId;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
