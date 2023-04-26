package cart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductModificationRequest {

    private final Long id;
    private final String name;
    private final String image;
    private final int price;

    @JsonCreator
    public ProductModificationRequest(
            @JsonProperty(value = "id") final Long id,
            @JsonProperty(value = "name") final String name,
            @JsonProperty(value = "image") final String image,
            @JsonProperty(value = "price") final int price
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

    public int getPrice() {
        return price;
    }
}
