package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.product.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private String description;

    private ProductResponse() {
        this(null, null, null, null, null);
    }

    private ProductResponse(Long id, String name, Integer price, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getDescription()
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

    public String getDescription() {
        return description;
    }
}
