package cart.controller.dto;

import cart.domain.Item;

import java.util.Objects;

public class ItemResponse {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    private ItemResponse(Long id, String name, String imageUrl, Integer price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static ItemResponse from(final Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getImageUrl(),
                item.getPrice()
        );
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemResponse that = (ItemResponse) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(imageUrl, that.imageUrl)
                && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, price);
    }

    @Override
    public String toString() {
        return "ItemResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }
}
