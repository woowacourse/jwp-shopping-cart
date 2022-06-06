package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProductRequest {
    @NotBlank
    private String name;
    @NotBlank
    private int price;
    private String image;

    public ProductRequest() {
    }

    public ProductRequest(String name, int price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
