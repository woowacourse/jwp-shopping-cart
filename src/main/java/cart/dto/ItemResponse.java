package cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemResponse {

    private final Long id;
    private final String name;
    @JsonProperty("image-url")
    private final String imageUrl;
    private final int price;

    public ItemResponse(final Long id, final String name, final String imageUrl, final int price) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
