package cart.product.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {

    private final Long id;
    private final Name name;
    private final ImageUrl imageUrl;
    private final Price price;

    public static Product of(final String name, final String imageUrl, final Integer price) {
        return new Product(null, Name.from(name), ImageUrl.from(imageUrl), Price.from(price));
    }

    public static Product of(final Long id, final String name, final String imageUrl, final Integer price) {
        return new Product(id, Name.from(name), ImageUrl.from(imageUrl), Price.from(price));
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
