package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private ThumbnailImage thumbnailImage;

    public OrderDetail() {
    }

    public OrderDetail(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderDetail(final Product product, final int quantity) {
        this(product.getId(), product.getPrice(), product.getName(), product.getThumbnailImage(), quantity);
    }

    public OrderDetail(final Long productId, final int price, final String name,
                       final ThumbnailImage thumbnailImage, final int quantity) {
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.thumbnailImage = thumbnailImage;
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

    public ThumbnailImage getImage() {
        return thumbnailImage;
    }

    public int getQuantity() {
        return quantity;
    }
}
