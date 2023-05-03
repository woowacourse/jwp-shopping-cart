package cart.domain.product;

public class Product {
    private final ProductName name;
    private final Price price;
    private final ImageUrl imageUrl;

    public Product(ProductName name, Price price, ImageUrl imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name.getName();
    }

    public int getPrice() {
        return price.getPrice();
    }

    public String getImageUrl() {
        return imageUrl.getImageUrl().toString();
    }
}
