package woowacourse.shoppingcart.dto.product;

public class ProductRequest {

    private final String name;
    private final Integer price;
    private final String imageUrl;

    public ProductRequest(final String name, final Integer price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
