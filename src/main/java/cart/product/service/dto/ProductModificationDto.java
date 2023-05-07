package cart.product.service.dto;

import cart.product.domain.ImageUrl;
import cart.product.domain.Product;
import cart.product.domain.ProductCategory;
import cart.product.domain.ProductId;
import cart.product.domain.ProductName;
import cart.product.domain.ProductPrice;

public class ProductModificationDto {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String category;
    private final String imageUrl;

    public ProductModificationDto(final Long id, final String name, final Integer price, final String category,
                                  final String imageUrl) {
        this.id = id;
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
                ImageUrl.from(imageUrl),
                ProductId.from(id)
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
