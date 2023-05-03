package cart.web.controller.product.dto;

import cart.domain.product.Product;
import cart.domain.product.ProductCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank(message = "상품 이름은 비어있을 수 없습니다.")
    private final String name;

    private final String imageUrl;

    @NotNull(message = "상품 가격은 비어있을 수 없습니다.")
    private final Integer price;

    @NotNull(message = "상품 카테고리는 비어있을 수 없습니다.")
    private final ProductCategory category;

    public ProductRequest(final String name, final String imageUrl, final Integer price, final ProductCategory category) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }


    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public Product toEntity() {
        return new Product(name, imageUrl, price, category);
    }

    public Product toEntity(final ProductRequest productRequest) {
        return new Product(
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice(),
                productRequest.getCategory());
    }
}
