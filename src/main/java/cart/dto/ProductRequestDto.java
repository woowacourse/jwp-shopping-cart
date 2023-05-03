package cart.dto;

import cart.entity.Product;
import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class ProductRequestDto {

    @Length(min = 1, max = 100)
    @NotBlank
    private final String name;

    @Positive
    @Range(min = 1, max = 1_000_000_000)
    private final Integer price;

    @NotEmpty
    @NotBlank
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
