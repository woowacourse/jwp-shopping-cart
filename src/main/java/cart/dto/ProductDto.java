package cart.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ProductDto {

    private final Long id;
    @NotNull
    private final String name;
    @NotNull
    private final String imageUrl;
    @NotNull
    private final Integer price;

    public ProductDto(final String name, final String imageUrl, final Integer price) {
        this(null, name, imageUrl, price);
    }

    public ProductDto(final Long id, final String name, final String imageUrl, final Integer price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
