package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private int quantity;
    private Long cartId;

    public ProductResponse() {}

    public ProductResponse(Long id, String name, Integer price, String imageUrl, int quantity, Long cartId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.cartId = cartId;
    }

    public ProductResponse(Product product, int quantity, Long cartId) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity, cartId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getCartId() {
        return cartId;
    }
}
