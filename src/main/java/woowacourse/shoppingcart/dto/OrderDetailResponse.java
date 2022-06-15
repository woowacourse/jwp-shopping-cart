package woowacourse.shoppingcart.dto;

public class OrderDetailResponse {

    private final long id;
    private final String name;
    private final int cost;
    private final int quantity;
    private final String imageUrl;

    public OrderDetailResponse(long id, String name, int cost, int quantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
