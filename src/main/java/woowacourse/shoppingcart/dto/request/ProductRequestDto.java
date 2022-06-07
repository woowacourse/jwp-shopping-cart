package woowacourse.shoppingcart.dto.request;

public class ProductRequestDto {
    private final String name;
    private final Integer price;
    private final String thumbnailUrl;

    public ProductRequestDto(String name, Integer price, String thumbnailUrl) {
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
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
}
