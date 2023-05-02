package cart.domain;

public class Product {

    private final ProductName name;
    private final String imageUrl;
    private final ProductPrice price;
    private final ProductCategory category;

    private Product(final ProductName name, final String imageUrl,
                    final ProductPrice price, final ProductCategory category) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public static Product create(final String name, final String imageUrl,
                                 final int price, final String category) {
        return new Product(ProductName.create(name), imageUrl,
            ProductPrice.create(price), ProductCategory.from(category));
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
