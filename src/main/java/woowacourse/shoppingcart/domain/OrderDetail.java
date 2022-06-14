package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private Long productId;
    private Quantity quantity;
    private int price;
    private String name;
    private String imageUrl;

    public OrderDetail() {
    }

    public OrderDetail(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = new Quantity(quantity);
    }

    public OrderDetail(final Product product, final Quantity quantity) {
        this(product.getId(), product.getPrice(), product.getName(), product.getImageUrl(), quantity);
    }

    public OrderDetail(final Long productId, final int price, final String name,
                       final String imageUrl, final int quantity) {
        this(productId, price, name, imageUrl, new Quantity(quantity));
    }

    public OrderDetail(final Long productId, final int price, final String name,
                       final String imageUrl, final Quantity quantity) {
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
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

    public Quantity getQuantity() {
        return quantity;
    }
}
