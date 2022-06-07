package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Product;

public class OrderDetailResponse {

    private Long productId;
    private int price;
    private String name;
    private String imageUrl;
    private int quantity;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(final Product product, final int quantity) {
        this(product.getId(), product.getPrice(), product.getName(), product.getImageUrl(), quantity);
    }

    public OrderDetailResponse(final Long productId, final int price, final String name, final String imageUrl,
                               final int quantity) {
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
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
