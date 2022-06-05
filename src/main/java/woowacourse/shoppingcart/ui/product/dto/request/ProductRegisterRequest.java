package woowacourse.shoppingcart.ui.product.dto.request;

import woowacourse.shoppingcart.application.dto.ProductSaveServiceRequest;

public class ProductRegisterRequest {

    private String name;
    private Integer price;
    private String imageUrl;

    public ProductRegisterRequest() {
    }

    public ProductRegisterRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductSaveServiceRequest toServiceDto() {
        return new ProductSaveServiceRequest(name, price, imageUrl);
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
