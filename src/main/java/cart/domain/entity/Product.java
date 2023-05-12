package cart.domain.entity;

import cart.domain.Price;

public class Product {

    private final Long id;
    private final String name;
    private final String image;
    private final Price price;

    private Product(final Long id, final String name, final String image, final Price price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static Product of(final String name, final String image, final int price) {
        return new Product(null, name, image, new Price(price));
    }

    public static Product of(final Long id, final String name, final String image, final int price) {
        return new Product(id, name, image, new Price(price));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price.getPrice();
    }
}
