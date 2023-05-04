package cart.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductRequest {

    @NotEmpty
    private final String name;
    @NotNull
    private final Integer price;
    @NotBlank
    private final String imageUrl;

    public ProductRequest(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }
}
