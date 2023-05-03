package cart.controller.dto;

import cart.dao.entity.ItemEntity;

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

    public static ItemResponse from(ItemEntity itemEntity) {
        return new ItemResponse(itemEntity.getId(), itemEntity.getName(), itemEntity.getImageUrl(), itemEntity.getPrice());
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
