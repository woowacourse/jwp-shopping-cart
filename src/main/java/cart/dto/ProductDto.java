package cart.dto;

import cart.domain.Product;

public class ProductDto {

    private final Long id;
    private final String name;
    private final String imgUrl;
    private final int price;

    private ProductDto(final Long id,
                       final String name,
                       final String imgUrl,
                       final int price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static ProductDto from(final Product product) {
        return new ProductDto(product.getId(),
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
