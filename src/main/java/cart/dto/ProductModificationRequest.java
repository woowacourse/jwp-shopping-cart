package cart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductModificationRequest {

    @NotNull
    private final Long id;

    @NotBlank
    private final String name;

    @NotBlank
    private final String image;

    @Max(value = 10_000_000, message = "상품 등록은 최대 천만원까지 가능합니다.")
    @Positive
    private final Integer price;

    @JsonCreator
    public ProductModificationRequest(
            @JsonProperty(value = "id") final Long id,
            @JsonProperty(value = "name") final String name,
            @JsonProperty(value = "image") final String image,
            @JsonProperty(value = "price") final Integer price
    ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Long getId() {
        return id;
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
