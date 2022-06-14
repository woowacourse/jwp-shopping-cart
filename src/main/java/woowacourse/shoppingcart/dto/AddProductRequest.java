package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class AddProductRequest {
    @NotBlank(message = "이름은 빈 값일 수 없습니다.")
    private String name;
    @Positive(message = "금액은 양의 정수만 허용합니다.")
    private int price;
    @NotBlank(message = "이미지 주소는 빈 값일 수 없습니다.")
    private String imageUrl;

    public AddProductRequest() {
    }

    public AddProductRequest(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
