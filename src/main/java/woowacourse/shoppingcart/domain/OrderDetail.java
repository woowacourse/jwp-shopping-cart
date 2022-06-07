package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private Long id;
    private Quantity quantity;
    private Long productId;
    private int price;
    private String name;
    private String imageUrl;

    private OrderDetail() {
    }

    public OrderDetail(final int quantity, final Long productId, final int price, final String name,
                       final String imageUrl) {
        this.quantity = new Quantity(quantity);
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public OrderDetail(final Long id, final int quantity, final Long productId, final int price, final String name,
                       final String imageUrl) {
        this(quantity, productId, price, name, imageUrl);
        this.id = id;
    }

    public static OrderDetail from(final CartItem cartItem) {
        return new OrderDetail(cartItem.getQuantity(), cartItem.getProduct().getId(),
                cartItem.getProduct().getPrice(), cartItem.getProduct().getName(), cartItem.getProduct().getImageUrl());
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
