package cart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductModificationRequest {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    @JsonCreator
    public ProductModificationRequest(
            @JsonProperty(value = "id") final Long id,
            @JsonProperty(value = "name") final String name,
            @JsonProperty(value = "image") final String imageUrl,
            @JsonProperty(value = "price") final Integer price
    ) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Long getId() {
        return id;
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
