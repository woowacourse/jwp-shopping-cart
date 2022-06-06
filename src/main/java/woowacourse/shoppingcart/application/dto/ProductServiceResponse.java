package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductServiceResponse {

    private long id;
    private String name;
    private int price;
    private int quantity;
    private String imageUrl;

    public ProductServiceResponse() {
    }

    public ProductServiceResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getStock();
        this.imageUrl = product.getImageUrl();
    }

    public long getId() {
        return id;
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

    public String getImageUrl() {
        return imageUrl;
    }
}
