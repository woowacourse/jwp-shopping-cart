package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductServiceResponse {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public ProductServiceResponse(final Long id,
                                  final String name,
                                  final Integer price,
                                  final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductServiceResponse from(final Product product) {
        return new ProductServiceResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
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
