package woowacourse.shoppingcart.dto.order;

public class OrderDetailDto {
    private Long productId;
    private String name;
    private int quantity;
    private int price;
    private String image;

    public OrderDetailDto(Long productId, String name, int quantity, int price, String image) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
