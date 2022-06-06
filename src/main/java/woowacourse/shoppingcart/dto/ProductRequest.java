package woowacourse.shoppingcart.dto;

public class ProductRequest {

    private String name;
    private int price;
    private String url;

    public ProductRequest() {
    }

    public ProductRequest(String name, int price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }
}
