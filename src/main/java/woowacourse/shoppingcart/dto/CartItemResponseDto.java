package woowacourse.shoppingcart.dto;

public class CartItemResponseDto {
    
    private Long productId;
    private String thumbnailUrl;
    private String name;
    private Integer price;
    private Integer quantity;
    private Integer count;

    private CartItemResponseDto() {
    }

    public CartItemResponseDto(final Long productId,
                               final String thumbnailUrl,
                               final String name,
                               final Integer price,
                               final Integer quantity,
                               final Integer count) {
        this.productId = productId;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.count = count;
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

    public Integer getCount() {
        return count;
    }
}
