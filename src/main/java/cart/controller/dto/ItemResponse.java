package cart.controller.dto;

import cart.model.Item;

public class ItemResponse {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    private ItemResponse(Long id, String name, String imageUrl, int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static ItemResponse from(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getImageUrl(), item.getPrice());
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
