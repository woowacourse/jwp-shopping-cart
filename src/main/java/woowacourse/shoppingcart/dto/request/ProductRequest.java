package woowacourse.shoppingcart.dto.request;

public class ProductRequest {

    private String name;
    private Integer price;
    private String imageUrl;
    private String description;
    private int stock;

    public ProductRequest() {
    }

    public ProductRequest(final String name, final int price, final String imageUrl, final String description,
                          final int stock) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }
}
