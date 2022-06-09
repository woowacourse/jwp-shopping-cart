package woowacourse.shoppingcart.dto.request;

public class ProductRequestDto {

    private String name;
    private Integer price;
    private String thumbnailUrl;
    private Integer quantity;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, Integer price, String thumbnailUrl, final Integer quantity) {
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
