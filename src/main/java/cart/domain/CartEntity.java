package cart.domain;

public class CartEntity {

    private final int id;
    private final ProductEntity product;

    public CartEntity(int id, ProductEntity product) {
        this.id = id;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public static class Builder {

        private int id;
        private ProductEntity product;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder product(ProductEntity product) {
            this.product = product;
            return this;
        }

        public CartEntity build() {
            return new CartEntity(id, product);
        }
    }

}
