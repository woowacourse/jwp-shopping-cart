package cart.dto;

public class ProductSaveRequest {
    private final String name;
    private final Integer price;
    private final String imgUrl;

    public ProductSaveRequest(String name, Integer price, String imgUrl) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
