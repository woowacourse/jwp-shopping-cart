package cart.dto;

import cart.domain.Product;

public class ProductResponseDto {

    private Long id;
    private String name;
    private String imgUrl;
    private int price;

    private ProductResponseDto(final Long id,
                               final String name,
                               final String imgUrl,
                               final int price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static ProductResponseDto from(final Product product) {
        return new ProductResponseDto(product.getId(),
                product.getName(),
                product.getImgUrl(),
                product.getPrice()
        );
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
