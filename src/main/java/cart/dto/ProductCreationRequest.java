package cart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductCreationRequest {

    private final String name;
    private final String imageUrl;
    private final Integer price;

    @JsonCreator
    public ProductCreationRequest(
            @JsonProperty(value = "name") final String name,
            @JsonProperty(value = "image") final String imageUrl,
            @JsonProperty(value = "price") final Integer price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }
}
