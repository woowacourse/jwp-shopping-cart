package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.ProductServiceResponse;

public class ProductResponse {

    private long id;
    private String name;
    private int price;
    private int quantity;
    private String imageUrl;

    public ProductResponse() {
    }

    public ProductResponse(ProductServiceResponse productServiceResponse) {
        this.id = productServiceResponse.getId();
        this.name = productServiceResponse.getName();
        this.price = productServiceResponse.getPrice();
        this.quantity = productServiceResponse.getQuantity();
        this.imageUrl = productServiceResponse.getImageUrl();
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
