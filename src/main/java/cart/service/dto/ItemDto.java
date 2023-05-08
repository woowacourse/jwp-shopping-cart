package cart.service.dto;

import cart.domain.item.Item;

public class ItemDto {

    private final long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    private ItemDto(final long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static ItemDto from(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getImageUrl(), item.getPrice());
    }

    public long getId() {
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
