package woowacourse.shoppingcart.dto;

public class ProductRequest {
    private String name;
    private int price;
    private int stock;
    private String imageURL;

    private ProductRequest() {
    }

    public ProductRequest(String name, int price, int stock, String imageURL) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageURL() {
        return imageURL;
    }
}
