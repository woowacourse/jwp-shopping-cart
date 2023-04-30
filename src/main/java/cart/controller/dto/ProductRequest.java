package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class ProductRequest {

    @NotBlank(message = "상품의 이름은 공백이어서는 안됩니다.")
    private String name;

    @Pattern(regexp = "^(http:\\/\\/|https:\\/\\/)?(www\\.)?[a-zA-Z0-9]+(\\.[a-zA-Z]+)+([/?].*)?$", message = "주소는 http(s):// 로 시작해야 합니다.")
    private String imageUrl;

    @Positive(message = "가격은 양수여야 합니다.")
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
