package woowacourse.shoppingcart.dto.response;

public class ProductResponse {
    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public ProductResponse() {
    }

    public ProductResponse(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
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
