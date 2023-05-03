package cart.entity;

public class CartItem {

    private final int id;
    private final Product product;

    public CartItem(int id, Product product) {
        this.id = id;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public static class Builder {

        private int id;
        private Product product;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public CartItem build() {
            return new CartItem(id, product);
        }

    }

}
