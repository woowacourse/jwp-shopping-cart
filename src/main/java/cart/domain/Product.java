package cart.domain;

public class Product {
    private final Long id;
    private final ProductName name;
    private final String imageUrl;
    private final ProductPrice price;
    private final ProductCategory category;

    private Product(final ProductName name, final String imageUrl,
                    final ProductPrice price, final ProductCategory category) {
        this(null, name, imageUrl, price, category);
    }

    private Product(final Long id, final ProductName name, final String imageUrl,
                    final ProductPrice price, final ProductCategory category) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public static Product create(final String name, final String imageUrl, final int price, final String category) {
        return new Product(ProductName.create(name), imageUrl,
            ProductPrice.create(price), ProductCategory.from(category));
    }

    public static Product createWithId(final long id, final String name, final String imageUrl,
                                       final int price, final String category) {
        return new Product(id, ProductName.create(name), imageUrl,
            ProductPrice.create(price), ProductCategory.from(category));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price.getPrice();
    }

    public ProductCategory getCategory() {
        return category;
    }
}
