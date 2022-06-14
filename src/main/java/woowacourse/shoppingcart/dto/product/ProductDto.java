package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.Product;

public class ProductDto {
    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public ProductDto() {
    }

    public ProductDto(final Long id, final String name, final Integer price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductDto from(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
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
