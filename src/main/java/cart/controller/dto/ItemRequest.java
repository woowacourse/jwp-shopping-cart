package cart.controller.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ItemRequest {

    @NotEmpty(message = "이름을 입력해주세요.")
    @Length(max = 30, message = "이름은 30자 이하로 입력해주세요.")
    private final String name;

    @NotNull(message = "가격을 입력해주세요.")
    @Positive(message = "가격은 양수로 입력해주세요.")
    @Max(value = 1_000_000_000, message = "가격은 10억 이하로 입력해주세요.")
    private final Integer price;

    @Length(max = 5000, message = "URL은 5000자 이하로 입력해주세요.")
    private final String imageUrl;

    public ItemRequest(String name, Integer price, String imageUrl) {
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
