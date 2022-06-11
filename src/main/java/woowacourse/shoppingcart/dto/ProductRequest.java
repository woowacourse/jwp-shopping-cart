package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank
    private final String name;
    @NotBlank
    private final int price;
    private final String imageUrl;

    public ProductRequest(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
