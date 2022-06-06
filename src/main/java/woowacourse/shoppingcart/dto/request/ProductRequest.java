package woowacourse.shoppingcart.dto.request;

public class ProductRequest {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
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

    public Long getId() {
        return id;
    }
}
