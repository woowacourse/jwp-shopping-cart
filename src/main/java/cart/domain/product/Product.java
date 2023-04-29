package cart.domain.product;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Product {

    private final Long id;
    private final ProductName name;
    private final ProductImageUrl imageUrl;
    private final Money price;

    public Product(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    public Product(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = new ProductName(name);
        this.imageUrl = new ProductImageUrl(imageUrl);
        this.price = new Money(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getProductName();
    }

    public String getImageUrl() {
        return imageUrl.getProductImageUrl();
    }

    public int getPrice() {
        return price.getAmount();
    }
}
