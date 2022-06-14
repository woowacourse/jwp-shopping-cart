package woowacourse.shoppingcart.dto.request;

public class ProductRequest {
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public ProductRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
}
