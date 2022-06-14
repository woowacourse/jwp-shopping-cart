package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.ProductRequest;

public class ProductDto {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private String description;
    private int stock;

    public ProductDto() {
    }

    public ProductDto(final Long id, final String name, final int price, final String imageUrl,
                      final String description, final int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
    }

    public static ProductDto from(final ProductRequest request) {
        return new ProductDto(null, request.getName(), request.getPrice(), request.getImageUrl(),
                request.getDescription(), request.getStock());
    }

    public static Product toProduct(final ProductDto productDto) {
        return new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl(),
                productDto.getDescription(), productDto.getStock());
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

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }
}
