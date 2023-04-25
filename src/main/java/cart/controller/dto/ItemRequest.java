package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ItemRequest {

    @NotBlank(message = "이름에 공백이 입력될 수 없습니다.")
    private String name;

    @NotBlank(message = "이미지 URL은 공백이 입력될 수 없습니다.")
    private String imageUrl;

    @Positive(message = "가격은 음수가 입력될 수 없습니다.")
    private int price;

    private ItemRequest() {
    }

    public ItemRequest(String name, String imageUrl, int price) {
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
