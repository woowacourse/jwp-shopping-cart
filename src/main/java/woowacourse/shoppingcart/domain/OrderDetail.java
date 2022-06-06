package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;

public class OrderDetail {
    private final Long productId;
    private final int price;
    private final String name;
    private final String imageUrl;
    private final int quantity;

    public OrderDetail() {
        this(null, 0, null, null, 0);
    }

    public OrderDetail(Long productId, int quantity) {
        this(productId, 0, null, null, quantity);
    }

    public OrderDetail(Product product, int quantity) {
        this(product.getId(), product.getPrice(), product.getName(), product.getImageUrl(), quantity);
    }

    public OrderDetail(Long productId, int price, String name, String imageUrl, int quantity) {
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

    public int getQuantity() {
        return quantity;
    }
}
