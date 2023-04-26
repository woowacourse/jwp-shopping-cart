package cart.controller.dto;

public class SaveRequest {
    private final String name;
    private final long price;
    private final String imageUrl;

    public SaveRequest(String name, long price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
