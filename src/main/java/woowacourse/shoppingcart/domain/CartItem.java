package woowacourse.shoppingcart.domain;

public class CartItem {

    private Long id;
    private Long customerId;
    private Long productId;
    private String name;
    private Price price;
    private Quantity quantity;
    private String imageUrl;

    public CartItem(Long id, Long customerId, Long productId, String name, int price, int quantity, String imageUrl) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.name = name;
        this.price = new Price(price);
        this.quantity = new Quantity(quantity);
        this.imageUrl = imageUrl;
    }

    public static CartItem from(Long customerId, Product product) {
        return new CartItem(null, customerId, product.getId(), product.getName(), product.getPrice(), 1, product.getImageUrl());
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
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
