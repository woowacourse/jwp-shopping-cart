package cart.domain;

import cart.dto.ProductDto;

public class ProductEntity {

    private int id;
    private String name;
    private String image;
    private int price;

    public ProductEntity(final String name, final String image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public ProductEntity(final int id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public ProductEntity update(final ProductDto productDto) {
        this.name = productDto.getName();
        this.image = productDto.getImage();
        this.price = productDto.getPrice();
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public static class Builder {

        private int id;
        private String name;
        private String image;
        private int price;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public ProductEntity build() {
            return new ProductEntity(id, name, image, price);
        }
    }

}
