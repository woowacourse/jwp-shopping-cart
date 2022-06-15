package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Product;

public class OrderDetailResponse {

    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String imageUrl;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(Long productId, int quantity, int price, String name, String imageUrl) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public OrderDetailResponse(final Product product, final int quantity) {
        this(product.getId(), quantity, product.getPrice(), product.getName(), product.getImageUrl());
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
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
}
