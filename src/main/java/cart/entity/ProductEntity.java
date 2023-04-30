package cart.entity;

import cart.domain.product.Image;
import cart.domain.product.Name;
import cart.domain.product.Price;

public class ProductEntity {

    private final Long id;
    private final Name name;
    private final Image image;
    private final Price price;

    private ProductEntity(final Long id, final String name, final String image, final Integer price) {
        this.id = id;
        this.name = Name.from(name);
        this.image = Image.from(image);
        this.price = Price.from(price);
    }

    public static ProductEntity of(final String name, final String image, final Integer price) {
        return new ProductEntity(null, name, image, price);
    }

    public static ProductEntity of(final Long id, final String name, final String image, final Integer price) {
        return new ProductEntity(id, name, image, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getImage() {
        return image.getImage();
    }

    public Integer getPrice() {
        return price.getPrice();
    }
}
