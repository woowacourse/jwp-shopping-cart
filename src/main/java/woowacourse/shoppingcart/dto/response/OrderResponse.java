package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Product;

public class OrderResponse {
    private Long id;
    private String name;
    private int cost;
    private int quantity;
    private String imageUrl;

    public OrderResponse() {
    }

    public OrderResponse(final Long id, final int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public OrderResponse(final Product product, final int quantity) {
        this(product.getId(), product.getPrice(), product.getName(), product.getImageUrl(), quantity);
    }

    public OrderResponse(final Long id, final int cost, final String name,
                       final String imageUrl, final int quantity) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public int getCost() {
        return cost;
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
