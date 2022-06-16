package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {
    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private Long cartId;
    private int quantity;

    private ProductResponse() {
    }

    public ProductResponse(Long id, String name, Integer price, String imageUrl, Long cartId, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public static ProductResponse of(Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), null, 0);
    }

    public static ProductResponse of(Product product, Long cartId, int productQuantity){
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), cartId, productQuantity);
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

    public Long getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }
}
