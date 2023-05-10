package cart.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductRegisterRequest {

    @NotBlank(message = "이름은 공백이 될 수 없습니다.")
    private String name;

    @Positive(message = "가격은 양수만 가능합니다.")
    private int price;

    @NotBlank(message = "imageUrl은 공백이 될 수 없습니다.")
    private String imageUrl;

    public ProductRegisterRequest() {
    }

    public ProductRegisterRequest(final String name, final int price, final String imageUrl) {
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
