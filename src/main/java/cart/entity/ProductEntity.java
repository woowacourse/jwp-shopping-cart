package cart.entity;

import cart.domain.product.ImageUrl;
import cart.domain.product.Name;
import cart.domain.product.Price;

public class ProductEntity {

    private final Long id;
    private final Name name;
    private final ImageUrl imageUrl;
    private final Price price;

    private ProductEntity(final Long id, final String name, final String imageUrl, final Integer price) {
        this.id = id;
        this.name = Name.from(name);
        this.imageUrl = ImageUrl.from(imageUrl);
        this.price = Price.from(price);
    }

    public static ProductEntity of(final String name, final String imageUrl, final Integer price) {
        return new ProductEntity(null, name, imageUrl, price);
    }

    public static ProductEntity of(final Long id, final String name, final String imageUrl, final Integer price) {
        return new ProductEntity(id, name, imageUrl, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getImageUrl() {
        return imageUrl.getUrl();
    }

    public Integer getPrice() {
        return price.getPrice();
    }
}
