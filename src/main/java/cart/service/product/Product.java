package cart.service.product;

public class Product {

    private final Long id;
    private final ProductName name;
    private final ProductImage imageUrl;
    private final ProductPrice price;

    public Product(Long id, ProductName name, ProductImage imageUrl, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(ProductName name, ProductImage imageUrl, ProductPrice price) {
        this(null, name, imageUrl, price);
    }

    public Product replaceProduct(ProductName name, ProductImage imageUrl, ProductPrice price, Long id) {
        return new Product(id, name, imageUrl, price);
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
