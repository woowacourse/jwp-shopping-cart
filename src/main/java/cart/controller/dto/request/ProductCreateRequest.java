package cart.controller.dto.request;

import cart.exception.CantSellNegativeQuantity;

import javax.validation.constraints.NotEmpty;

public class ProductCreateRequest {

    @NotEmpty(message = "Null을 허용하지 않습니다.")
    private String name;

    private int price;

    @NotEmpty(message = "Null을 허용하지 않습니다.")
    private String imageUrl;

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void validatePrice(int price) {
        if (price < 0) {
            throw CantSellNegativeQuantity.EXCEPTION;
        }
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
