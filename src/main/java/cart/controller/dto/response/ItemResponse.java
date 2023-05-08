package cart.controller.dto.response;

import cart.service.dto.ItemDto;

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

    public static ItemResponse from(ItemDto itemDto) {
        return new ItemResponse(itemDto.getId(), itemDto.getName(), itemDto.getImageUrl(), itemDto.getPrice());
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
