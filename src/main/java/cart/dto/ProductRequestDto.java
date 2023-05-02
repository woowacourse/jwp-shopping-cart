package cart.dto;

import cart.entity.Product;
import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductRequestDto {

    @NotNull
    private final String name;

    @Positive
    private final Integer price;

    @NotNull
    private final String imageUrl;

    public ProductRequestDto(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public Product toEntity() {
        return new Product.Builder()
                .name(Name.of(name))
                .price(Price.of(price))
                .imageUrl(Url.of(imageUrl))
                .build();
    }

}
