package woowacourse.shoppingcart.dto;

public class ProductRequest {

    private int price;
    private String name;
    private String imageUrl;

    private ProductRequest() {
    }

    public ProductRequest(int price, String name, String imageUrl) {
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
