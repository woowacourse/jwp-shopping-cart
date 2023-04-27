package cart.domain;

import cart.dao.ProductEntity;

public class Product {

    private final String name;
    private final String image;
    private final int price;

    public Product(final String name, final String image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
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

    public ProductEntity toEntity() {
        return new ProductEntity(name, image, price);
    }

}
