package cart.web.dto;

public class ProductCreateResponse {
    private Long id;
    private String name;
    private Integer price;
    private String category;
    private String imageUrl;

    public ProductCreateResponse() {
    }

    public ProductCreateResponse(Long id, ProductCreateRequest request) {
        this.id = id;
        this.name = request.getName();
        this.price = request.getPrice();
        this.category = request.getCategory();
        this.imageUrl = request.getImageUrl();
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
