package cart.controller.dto.request;

public class ProductUpdateRequest {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public ProductUpdateRequest() {

    }

    public ProductUpdateRequest(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
