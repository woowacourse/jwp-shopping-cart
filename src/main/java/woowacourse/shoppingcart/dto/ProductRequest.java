package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import woowacourse.shoppingcart.domain.ImageUrl;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ProductName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank
    private final String name;
    @NotNull
    private final Integer price;
    @NotBlank
    private final String imageUrl;

    @JsonCreator
    public ProductRequest(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toEntity() {
        return new Product(new ProductName(name), price, new ImageUrl(imageUrl));
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
