package cart.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class ProductUpdateRequest {

    @NotNull
    private final Long id;
    @NotEmpty
    private final String name;
    @NotNull
    private final BigDecimal price;
    @NotBlank
    private final String imageUrl;

    public ProductUpdateRequest(Long id, String name, BigDecimal price, String imageUrl) {
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

    public BigDecimal getPrice() {
        return price;
    }
}
