package woowacourse.shoppingcart.dto.request;

public class ProductRequestDto {
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public ProductRequestDto(String name, Integer price, String imageUrl) {
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
