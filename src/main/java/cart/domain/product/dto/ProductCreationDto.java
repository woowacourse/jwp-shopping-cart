package cart.domain.product.dto;

import cart.domain.product.ImageUrl;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.domain.product.ProductName;
import cart.domain.product.ProductPrice;

public class ProductCreationDto {
    private final String name;
    private final Integer price;
    private final String category;
    private final String imageUrl;

    public ProductCreationDto(String name, Integer price, String category, String imageUrl) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Product toProduct() {
        return new Product(
                ProductName.from(name),
                ProductPrice.from(price),
                ProductCategory.valueOf(category),
                ImageUrl.from(imageUrl)
        );
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
