package woowacourse.shoppingcart.entity;

public class ProductEntity {

    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductEntity(Long productId, String name, int price, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductEntity(String name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
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

    public String getImageUrl() {
        return imageUrl;
    }
}
