package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private Long id;
    private Quantity quantity;
    private Long orderId;
    private Long productId;
    private int price;
    private String name;
    private String imageUrl;

    private OrderDetail() {
    }

    public OrderDetail(final int quantity, final Long orderId, final Long productId, final int price, final String name,
                       final String imageUrl) {
        this(null, quantity, orderId, productId, price, name, imageUrl);
    }

    public OrderDetail(final Long id, final int quantity, final Long orderId, final Long productId, final int price, final String name,
                       final String imageUrl) {
        this.id = id;
        this.quantity = new Quantity(quantity);
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static OrderDetail from(final CartItem cartItem) {
        return new OrderDetail(cartItem.getQuantity(), null, cartItem.getProduct().getId(),
                cartItem.getProduct().getPrice(), cartItem.getProduct().getName(), cartItem.getProduct().getImageUrl());
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }
}
