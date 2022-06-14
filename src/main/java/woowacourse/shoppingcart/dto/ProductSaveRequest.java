package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductSaveRequest {

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private Integer price;

    @NotNull
    private String imageUrl;

    private ProductSaveRequest() {
    }

    public ProductSaveRequest(final String name, final Integer price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
