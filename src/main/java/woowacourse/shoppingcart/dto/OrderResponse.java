package woowacourse.shoppingcart.dto;

public class OrderResponse {

    private Long id;
    private Long productId;
    private String thumbnail;
    private String name;
    private Integer price;
    private Integer quantity;

    public OrderResponse() {
    }

    public OrderResponse(Long id, Long productId, String thumbnail, String name, Integer price, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.thumbnail = thumbnail;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getThumbnail() {
        return thumbnail;
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
