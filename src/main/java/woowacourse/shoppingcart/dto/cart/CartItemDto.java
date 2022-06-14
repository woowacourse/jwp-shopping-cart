package woowacourse.shoppingcart.dto.cart;

import woowacourse.shoppingcart.domain.Product;

public class CartItemDto {

    private Long productId;
    private String name;
    private int price;
    private String thumbnailUrl;
    private int quantity;
    private int count;

    private CartItemDto() {
    }

    public CartItemDto(Product product, int count) {
        this(product.getId(), product.getName(), product.getPrice(),
                product.getImageUrl(), product.getQuantity(), count);
    }

    public CartItemDto(Long productId, String name, int price, String thumbnailUrl, int quantity, int count) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
        this.count = count;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCount() {
        return count;
    }
}
