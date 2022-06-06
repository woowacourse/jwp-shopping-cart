package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.ProductRequest;

public class ProductDto {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public ProductDto() {
    }

    public ProductDto(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductDto from(final ProductRequest request) {
        return new ProductDto(null, request.getName(), request.getPrice(), request.getImageUrl());
    }

    public static Product toProduct(final ProductDto productDto) {
        return new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }
}
