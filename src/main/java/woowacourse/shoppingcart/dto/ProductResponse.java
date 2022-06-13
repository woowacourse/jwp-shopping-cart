package woowacourse.shoppingcart.dto;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    private ProductResponse() {
    }

    public ProductResponse(final Long id, final String name, final Integer price, final String imageUrl) {
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
