package woowacourse.shoppingcart.dto;

public class ProductResponseDto {

    private Long productId;
    private String thumbnailUrl;
    private String name;
    private Integer price;
    private Integer quantity;

    private ProductResponseDto() {
    }

    public ProductResponseDto(final Long productId, final String thumbnailUrl, final String name, final Integer price,
                              final Integer quantity) {
        this.productId = productId;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }
}
