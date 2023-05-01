package cart.domain.product;

public class Product {

    private final Long id;
    private final ProductName productName;
    private final ImageUrl imageUrl;
    private final Price price;
    private final ProductCategory category;

    public Product(final String productName, final String imageUrl, final Integer price, final ProductCategory category) {
        this(null, productName, imageUrl, price, category);
    }

    public Product(final Long id, final String productName, final String imageUrl, final Integer price, final ProductCategory category) {
        this.id = id;
        this.productName = new ProductName(productName);
        this.imageUrl = new ImageUrl(imageUrl);
        this.price = new Price(price);
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getProductNameValue() {
        return productName.getName();
    }

    public String getImageUrlValue() {
        return imageUrl.getImageUrl();
    }

    public Integer getPriceValue() {
        return price.getPrice();
    }

    public ProductCategory getCategory() {
        return category;
    }
}
