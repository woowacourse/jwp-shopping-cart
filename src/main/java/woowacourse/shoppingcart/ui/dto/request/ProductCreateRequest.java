package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import woowacourse.shoppingcart.service.dto.ProductCreateServiceRequest;

public class ProductCreateRequest {
    @NotBlank
    private String name;
    @PositiveOrZero
    private long price;
    @NotBlank
    private String imageUrl;

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(final String name, final long price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductCreateServiceRequest toServiceRequest() {
        return new ProductCreateServiceRequest(this.name, this.price, this.imageUrl);
    }
}
