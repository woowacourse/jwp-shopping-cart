package cart.dto.item;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

public class ItemSaveRequest {

    @NotBlank(message = "상품명은 공백일 수 없습니다.")
    @Size(max = 30, message = "상품명의 길이는 30자 이하여야합니다.")
    private final String name;

    @NotBlank(message = "이미지 url은 공백일 수 없습니다.")
    @JsonProperty("image-url")
    private final String imageUrl;

    @NotNull(message = "가격은 공백일 수 없습니다.")
    @Min(value = 0, message = "가격은 최소 0원 이상이어야합니다.")
    @Max(value = 1000000, message = "가격은 최대 100만원 이하여야합니다.")
    private final Integer price;

    public ItemSaveRequest(final String name, final String imageUrl, final Integer price) {
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

    public Integer getPrice() {
        return price;
    }
}
