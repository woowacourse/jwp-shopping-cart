package cart.dto.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class ProductRequest {

    @Size(min = 1, max = 255, message = "상품명은 1자 이상 255자 이하입니다. 입력값 : ${validatedValue}")
    @NotBlank(message = "상품명은 필수 입력값입니다.")
    private final String name;

    @Size(min = 1, max = 2048, message = "이미지 url은 1자 이상 2048자 이하입니다. 입력값 : ${validatedValue}")
    @NotBlank(message = "이미지 url은 필수 입력값입니다.")
    private final String image;

    @NotNull(message = "상품 가격은 필수 입력값입니다.")
    @PositiveOrZero(message = "상품 가격은 0원 이상이어야 합니다. 입력값 : ${validatedValue}")
    private final Integer price;

    public ProductRequest(final String name, final String image, final Integer price) {
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
