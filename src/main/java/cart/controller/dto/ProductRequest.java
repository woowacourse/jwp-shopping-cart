package cart.controller.dto;

import javax.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank(message = "상품의 이름은 공백이어서는 안됩니다.")
    private String name;

    @NotBlank(message = "상품의 주소는 공백이어서는 안됩니다.")
    private String imageUrl;

    private int price;

    public ProductRequest() {
    }

    public ProductRequest(final String name, final String imageUrl, final int price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
