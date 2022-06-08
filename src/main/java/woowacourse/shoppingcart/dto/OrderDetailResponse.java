package woowacourse.shoppingcart.dto;

public class OrderDetailResponse {

    private Long productId;
    private String name;
    private Integer quantity;
    private String imageUrl;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(Long productId, String name, Integer quantity, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
