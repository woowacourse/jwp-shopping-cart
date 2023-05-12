package cart.dao.entity;

public class CartProductEntity {

    private final Long cartId;
    private final String name;
    private final Integer price;
    private final String image;

    public CartProductEntity(Long cartId, String name, Integer price, String image) {
        this.cartId = cartId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Long getCartId() {
        return cartId;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public static class Builder {

        private Long cartId;
        private String name;
        private Integer price;
        private String image;

        public CartProductEntity build() {
            return new CartProductEntity(cartId, name, price, image);
        }

        public Builder id(final Long id) {
            this.cartId = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder price(final Integer price) {
            this.price = price;
            return this;
        }

        public Builder image(final String image) {
            this.image = image;
            return this;
        }
    }
}
