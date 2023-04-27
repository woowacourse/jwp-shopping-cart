package cart.web.dto.request;

public class ProductModificationRequest {

    private Long id;
    private String name;
    private Integer price;
    private String category;
    private String imageUrl;

    public ProductModificationRequest() {
    }

    public ProductModificationRequest(Long id, String name, Integer price, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
