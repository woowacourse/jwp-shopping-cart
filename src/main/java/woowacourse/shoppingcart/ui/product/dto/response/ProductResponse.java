package woowacourse.shoppingcart.ui.product.dto.response;

import woowacourse.shoppingcart.application.dto.ProductDetailServiceResponse;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public ProductResponse(final Long id, final String name, final Integer price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductResponse from(final ProductDetailServiceResponse serviceResponse) {
        return new ProductResponse(serviceResponse.getId(), serviceResponse.getName(), serviceResponse.getPrice(),
                serviceResponse.getImageUrl());
    }


    public Long getId() {
        return id;
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
