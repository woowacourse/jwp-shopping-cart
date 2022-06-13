package woowacourse.shoppingcart.dao.dto;

import woowacourse.shoppingcart.domain.product.Product;

public class OrderDetailDto {
    private final Long id;
    private final Long productId;
    private final int price;
    private final String name;
    private final String imageUrl;
    private final int quantity;

    public OrderDetailDto() {
        this(null, null, 0, null, null, 0);
    }

    public OrderDetailDto(Long id, Long productId, int quantity) {
        this(id, productId, 0, null, null, quantity);
    }

    public OrderDetailDto(Long id, Product product, int quantity) {
        this(id, product.getId(), product.getPrice(), product.getName(), product.getImageUrl(), quantity);
    }

    public OrderDetailDto(Long id, Long productId, int price, String name, String imageUrl, int quantity) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
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
