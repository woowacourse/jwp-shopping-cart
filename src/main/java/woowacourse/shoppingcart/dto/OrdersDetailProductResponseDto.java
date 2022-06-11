package woowacourse.shoppingcart.dto;

public class OrdersDetailProductResponseDto {

    private final Long productId;
    private final String thumbnailUrl;
    private final String name;
    private final Integer price;

    public OrdersDetailProductResponseDto(final Long productId,
                                          final String thumbnailUrl,
                                          final String name,
                                          final Integer price) {
        this.productId = productId;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}
