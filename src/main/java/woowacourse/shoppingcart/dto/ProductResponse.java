package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.application.dto.ProductServiceResponse;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    private ProductResponse() {
    }

    public ProductResponse(final Long id, final String name, final Integer price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductResponse from(final ProductServiceResponse serviceResponse) {
        return new ProductResponse(
                serviceResponse.getId(),
                serviceResponse.getName(),
                serviceResponse.getPrice(),
                serviceResponse.getImageUrl()
        );
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
