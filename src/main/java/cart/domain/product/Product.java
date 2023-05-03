package cart.domain.product;

import java.math.BigDecimal;

public class Product {
    private final ProductName name;
    private final ProductPrice price;
    private final ProductCategory category;
    private final ImageUrl imageUrl;
    private final ProductId productId;

    public Product(ProductName name, ProductPrice price, ProductCategory category, ImageUrl imageUrl) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.productId = ProductId.getEmptyId();
    }

    public Product(ProductName name, ProductPrice price, ProductCategory category, ImageUrl imageUrl,
                   ProductId productId) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.productId = productId;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public ProductCategory getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl.getUrl();
    }

    public Long getProductId() {
        return productId.getId();
    }
}
