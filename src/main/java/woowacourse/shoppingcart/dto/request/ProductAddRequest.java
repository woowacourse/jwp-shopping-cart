package woowacourse.shoppingcart.dto.request;

public class ProductAddRequest {

    private String name;
    private Integer price;
    private String imageUrl;

    private ProductAddRequest() {
    }

    public ProductAddRequest(final String name, final Integer price, final String imageUrl) {
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
