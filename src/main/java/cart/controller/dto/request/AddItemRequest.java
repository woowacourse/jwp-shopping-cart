package cart.controller.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AddItemRequest {

    @NotBlank(message = "이름에 공백이 입력될 수 없습니다.")
    private String name;

    @NotBlank(message = "이미지 URL은 공백이 입력될 수 없습니다.")
    private String imageUrl;

    @Positive(message = "가격은 양수만 입력할 수 있습니다.")
    @NotNull(message = "빈 값은 입력될 수 없습니다.")
    private Integer price;

    private AddItemRequest() {
    }

    public AddItemRequest(String name, String imageUrl, int price) {
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
