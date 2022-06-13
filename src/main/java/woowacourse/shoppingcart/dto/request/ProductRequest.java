package woowacourse.shoppingcart.dto.request;

public class ProductRequest {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    private ProductRequest() {
    }

    public ProductRequest(final Long id, final String name, final Integer price, final String imageUrl) {
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

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
