package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class CartResponse {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;

    public CartResponse() {
    }

    public CartResponse(Long id, Long productId, String name, int price, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public CartResponse(Long id, Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
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
