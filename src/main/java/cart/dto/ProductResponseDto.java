package cart.dto;

import cart.domain.Product;

public class ProductResponseDto {
    private final Long id;
    private final String name;
    private final String imgUrl;
    private final int price;

    private ProductResponseDto(Long id, String name, String imgUrl, int price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static ProductResponseDto of(String name, String imgUrl, int price) {
        return new ProductResponseDto(null, name, imgUrl, price);
    }

    public static ProductResponseDto fromProduct(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getImgUrl(), product.getPrice());
    }

    public static ProductResponseDto fromProductDto(ProductDto productDto) {
        return new ProductResponseDto(productDto.getId(), productDto.getName(), productDto.getImgUrl(), productDto.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price;
    }
}
