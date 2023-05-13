package cart.entity;

import java.util.Objects;

public class CartEntity {

    private final Integer id;
    private final Integer memberId;

    public CartEntity(Integer id, Integer memberId) {
        this.id = id;
        this.memberId = memberId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartEntity cart = (CartEntity) o;
        return Objects.equals(memberId, cart.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}
