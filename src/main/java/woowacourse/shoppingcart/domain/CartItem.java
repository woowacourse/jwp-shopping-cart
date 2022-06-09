package woowacourse.shoppingcart.domain;

public class CartItem {

    private Long id;
    private Long productId;
    private String name;
    private Price price;
    private Quantity quantity;
    private String imageUrl;

    public CartItem(Long id, Long productId, String name, int price, int quantity, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = new Price(price);
        this.quantity = new Quantity(quantity);
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price.getValue();
    }

    public int getQuantity() {
        return quantity.getValue();
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
