package cart.entity;

import cart.domain.Price;

public class ProductEntity {

    private final Long id;
    private final String name;
    private final String image;
    private final Price price;

    private ProductEntity(final Long id, final String name, final String image, final Price price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductEntity of(final String name, final String image, final int price) {
        return new ProductEntity(null, name, image, new Price(price));
    }

    public static ProductEntity of(final Long id, final String name, final String image, final int price) {
        return new ProductEntity(id, name, image, new Price(price));
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
