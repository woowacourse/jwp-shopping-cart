package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class CartResponse {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private Integer quantity;

    private CartResponse() {

    }

    public CartResponse(Product product, int quantity) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
    }

    private CartResponse(Long id, String name, Integer price, String imageUrl, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }
}
