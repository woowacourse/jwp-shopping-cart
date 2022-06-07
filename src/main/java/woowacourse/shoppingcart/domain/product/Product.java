package woowacourse.shoppingcart.domain.product;

public class Product {

    private final Long id;
    private final ProductName name;
    private final Price price;
    private final ProductImage imageUrl;
    private final Description description;

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl, null);
    }

    public Product(Long id, String name, Integer price, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }
}
