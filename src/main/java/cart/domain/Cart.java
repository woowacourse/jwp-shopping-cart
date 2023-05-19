package cart.domain;

import java.util.Objects;

public class Cart {

    private final Long id;
    private final Long userId;
    private final Long itemId;

    public static class Builder {
        private Long id;
        private Long userId;
        private Long itemId;

        public Builder() {
        }

        public Builder id(final Long value) {
            id = value;
            return this;
        }

        public Builder userId(final Long value) {
            userId = value;
            return this;
        }

        public Builder itemId(final Long value) {
            itemId = value;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }

    private Cart(Builder builder) {
        validate(builder);
        this.id = builder.id;
        this.userId = builder.userId;
        this.itemId = builder.itemId;
    }

    private void validate(Builder builder) {
        if (builder.userId == null) {
            throw new IllegalArgumentException("사용자 아이디는 빈 값일 수 없습니다.");
        }
        if (builder.itemId == null) {
            throw new IllegalArgumentException("상품 아이디는 빈 값일 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(userId, cart.userId) && Objects.equals(itemId, cart.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, itemId);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                '}';
    }
}
