package cart.web.controller.product.dto;

import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank(message = "상품 이름은 비어있을 수 없습니다.")
    private final String name;

    private final String imageUrl;

    @Range(min = 0, max = 1000000, message = "올바르지 않은 입력입니다. 입력 가능한 범위 : 0 ~ 999999")
    private final Integer price;

    @NotNull(message = "상품 카테고리는 비어있을 수 없습니다.")
    private final ProductCategory category;

    public ProductRequest(final String name, final String imageUrl, final Integer price, final ProductCategory category) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    /*@JsonCreator
    public ProductRequest(@JsonProperty("name") final String name,
                          @JsonProperty("imageUrl") final String imageUrl,
                          @JsonProperty("price") final Integer price,
                          @JsonProperty("category") final ProductCategory category) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }*/

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
}
