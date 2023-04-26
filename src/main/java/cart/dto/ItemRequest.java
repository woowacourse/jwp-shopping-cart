package cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
public class ItemRequest {

    @JsonProperty("name")
    private final String name;
    @JsonProperty("image-url")
    private final String imageUrl;
    @JsonProperty("price")
    private final int price;

    public ItemRequest(final String name, final String imageUrl, final int price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRequest that = (ItemRequest) o;
        return price == that.price && Objects.equals(name, that.name) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, imageUrl, price);
    }
}
