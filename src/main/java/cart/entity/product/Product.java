package cart.entity.product;

import cart.exception.common.NullOrBlankException;
import cart.exception.product.PriceNotUnderZeroException;

public class Product {

    private final Long id;
    private final Name name;
    private final ImageUrl imageUrl;
    private final Price price;

    public Product(final Long id, final Name name, final ImageUrl imageUrl, final Price price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(final String name, final String imageUrl, final int price) {
        this(
            null,
            new Name(name),
            new ImageUrl(imageUrl),
            new Price(price)
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getImageUrl() {
        return imageUrl.getImageUrl();
    }

    public int getPrice() {
        return price.getPrice();
    }
}
