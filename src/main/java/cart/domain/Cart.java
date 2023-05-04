package cart.domain;

public class Cart {

    private final Long id;
    private final Long userId;
    private final Long itemId;

    private Cart(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.itemId = builder.itemId;
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
}
