package cart.web.dto;

public class ProductCreateRequest {
    private String name;
    private Integer price;
    private String category;
    private String imageUrl;

    public ProductCreateRequest() {
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

    @Override
    public String toString() {
        return "ProductCreateRequest{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
