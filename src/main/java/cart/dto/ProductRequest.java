package cart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class ProductRequest {

    @Size(min = 1, max = 255, message = "상품명은 1자 이상 255자 이하입니다. 입력값 : ${validatedValue}")
    @NotBlank(message = "상품명은 필수 입력값입니다.")
    private String name;

    @Size(min = 1, max = 2048, message = "이미지 url은 1자 이상 2048자 이하입니다. 입력값 : ${validatedValue}")
    @NotBlank(message = "이미지 url은 필수 입력값입니다.")
    private String image;

    @PositiveOrZero(message = "상품 가격은 0원 이상이어야 합니다. 입력값 : ${validatedValue}")
    private int price;

    private ProductRequest() {
    }

    public ProductRequest(final String name, final String image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
