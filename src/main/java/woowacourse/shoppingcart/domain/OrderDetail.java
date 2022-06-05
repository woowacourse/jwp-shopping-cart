package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private Long id;
    private Long productId;
    private String name;
    private int price;
    private int quantity;
    private String imageUrl;

    public OrderDetail(final Long id, final Long productId, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderDetail(final OrderDetail orderDetail, final Product product) {
        this(orderDetail.id, product.getId(), product.getName(), product.getPrice(), orderDetail.quantity, product.getImageUrl());
    }

    public OrderDetail(final Long id, final Long productId, final String name, final int price, final int quantity,
                       final String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
