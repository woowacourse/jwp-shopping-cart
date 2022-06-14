package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.cart.Product;

public class ProductRequest {

    private String thumbnailUrl;
    private String name;
    private int price;
    private int quantity;

    public ProductRequest(String thumbnailUrl, String name, int price, int quantity) {
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    private ProductRequest() {
    }

    public Product toEntity() {
        return new Product(null, name, price, thumbnailUrl, quantity);
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
