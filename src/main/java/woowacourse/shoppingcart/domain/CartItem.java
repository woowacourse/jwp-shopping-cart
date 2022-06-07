package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;

public class CartItem {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;

    public CartItem() {
        this(null, null, null, 0, null);
    }

    public CartItem(Long id, Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public CartItem(Long id, Long productId, String name, int price, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
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

    public String getImageUrl() {
        return imageUrl;
    }
}
