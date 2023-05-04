package cart.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductUpdateRequest {

    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private Integer price;
    @NotBlank
    private String imageUrl;

    public ProductUpdateRequest(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
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
