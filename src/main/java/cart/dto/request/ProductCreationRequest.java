package cart.dto.request;

import cart.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductCreationRequest {

    @NotBlank
    private final String name;

    @NotBlank
    @URL
    private final String image;

    @Positive
    private final Integer price;

    @JsonCreator
    public ProductCreationRequest(
            @JsonProperty(value = "name") final String name,
            @JsonProperty(value = "image") final String image,
            @JsonProperty(value = "price") final Integer price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductDto toProductDto(final ProductCreationRequest request) {
        return ProductDto.of(request.name, request.image, request.price);
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
