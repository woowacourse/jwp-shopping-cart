package cart.product.dto;

import lombok.Getter;

@Getter
public class ProductDto {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    public ProductDto(final Long id, final String name, final String imageUrl, final Integer price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
