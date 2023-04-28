package cart.persistence.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Product {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;
    private final ProductCategory category;

    public final Logger log = LoggerFactory.getLogger(getClass());


    public Product(final Long id, final String name, final String imageUrl, final int price, final ProductCategory category) {
        validate(name, price, category);
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public Product(final String name, final String imageUrl, final int price, final ProductCategory category) {
        this(null, name, imageUrl, price, category);
    }

    private void validate(final String name, final int price, final ProductCategory category) {
        if (name.length() < 1 || name.length() > 25) {
            log.error("error = {}", "Invalid name length");
            throw new IllegalArgumentException();
        }
        if (price < 0 || price > 10_000_000) {
            log.error("error = {}", "Invalid price range");
            throw new IllegalArgumentException();
        }
        if (category == null) {
            log.error("error = {}", "ProductCategory can't be null");
            throw new IllegalArgumentException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public ProductCategory getCategory() {
        return category;
    }
}
