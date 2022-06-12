package woowacourse.shoppingcart.entity;

public class OrdersDetailEntity {
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final String description;
    private final int stock;
    private final int quantity;

    public OrdersDetailEntity(Long productId, String name, int price, String imageUrl, String description, int stock,
                              int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrdersDetailEntity{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", quantity=" + quantity +
                '}';
    }
}
