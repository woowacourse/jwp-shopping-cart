package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class CartResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int quantity;

    public CartResponse() {
        this(null, "", 0, "", 0);
    }

    public CartResponse(Product product, int quantity) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(),
            quantity);
    }

    public CartResponse(Long id, String name, int price, String imageUrl, int quantity) {
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

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}
