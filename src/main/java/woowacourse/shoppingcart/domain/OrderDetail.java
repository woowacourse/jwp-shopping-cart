package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private final Long id;
    private final Long productId;
    private final String name;
    private final Integer price;
    private final int quantity;
    private final String imageUrl;

    public OrderDetail(final Long id, final Long productId, final String name, final Integer price, final int quantity,
                       final String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public OrderDetail(final Long id, final Long productId, final int quantity) {
        this(id, productId, null, null, quantity, null);
    }

    public OrderDetail(final OrderDetail orderDetail, final Product product) {
        this(orderDetail.id, product.getId(), product.getName(), product.getPrice(), orderDetail.quantity, product.getImageUrl());
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
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
