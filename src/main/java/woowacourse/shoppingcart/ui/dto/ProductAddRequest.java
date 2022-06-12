package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.ProductSaveRequest;

public class ProductAddRequest {

    private String name;
    private int price;
    private String imageUrl;

    public ProductAddRequest() {
    }

    public ProductAddRequest(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductSaveRequest toServiceRequest() {
        return new ProductSaveRequest(name, price, imageUrl);
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
