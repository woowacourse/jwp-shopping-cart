package woowacourse.shoppingcart.dto;

public class CartProduct {
    private Long productId;
    private String image;
    private String name;
    private int price;
    private int quantity;

    public CartProduct(Long productId, String image, String name, int price, int quantity) {
        this.productId = productId;
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
