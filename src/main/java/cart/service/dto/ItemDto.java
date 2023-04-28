package cart.service.dto;

import cart.model.Item;

public class ItemDto {

    private final long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ItemDto(Item item) {
        id = item.getId();
        name = item.getName();
        imageUrl = item.getImageUrl();
        price = item.getPrice();
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
