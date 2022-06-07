package woowacourse.shoppingcart.domain;

public class OrderedProduct {
    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private ThumbnailImage thumbnailImage;

    public OrderedProduct(Long productId, int quantity, int price, String name,
                          ThumbnailImage thumbnailImage) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.thumbnailImage = thumbnailImage;
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

    public ThumbnailImage getThumbnailImage() {
        return thumbnailImage;
    }

    public int getQuantity() {
        return quantity;
    }
}
