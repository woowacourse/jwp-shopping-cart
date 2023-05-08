package cart.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class ProductRequestDto {

    @Length(min = 1, max = 100)
    @NotBlank
    private String name;

    @Positive
    @Range(min = 1, max = 1_000_000_000)
    private Integer price;

    @NotEmpty
    @NotBlank
    private String imageUrl;

    public ProductRequestDto() {
    }

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

    public ProductDto toDto() {
        return new ProductDto(name, price, imageUrl);
    }

}
