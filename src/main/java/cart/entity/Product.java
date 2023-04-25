package cart.entity;

public class Product {

    private final int id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public Product(int id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static class Builder {

        private int id;
        private String name;
        private int price;
        private String imageUrl;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Product build() {
            return new Product(id, name, price, imageUrl);
        }

    }

}
