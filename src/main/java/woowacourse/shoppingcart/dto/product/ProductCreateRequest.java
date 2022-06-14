package woowacourse.shoppingcart.dto.product;

public class ProductCreateRequest {

    private String name;
    private Integer price;
    private String thumbnailUrl;
    private Integer quantity;

    private ProductCreateRequest() {
    }

    public ProductCreateRequest(String name, int price, String thumbnailUrl, int quantity) {
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}
