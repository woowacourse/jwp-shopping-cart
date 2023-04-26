package cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.util.Objects;

public class ItemRequest {

    @NotBlank(message = "상품명은 공백일 수 없습니다.")
    @Size(max = 30, message = "상품명의 길이는 30자 이하여야합니다.")
    @JsonProperty("name")
    private final String name;

    @NotBlank(message = "이미지 url은 공백일 수 없습니다.")
    @JsonProperty("image-url")
    private final String imageUrl;

    @NotNull(message = "가격은 공백일 수 없습니다.")
    @Min(value = 0, message = "가격은 최소 0원 이상이어야합니다.")
    @Max(value = 1000000, message = "가격은 최대 100만원 이하여야합니다.")
    @JsonProperty("price")
    private final Integer price;

    public ItemRequest(final String name, final String imageUrl, final Integer price) {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRequest that = (ItemRequest) o;
        return price == that.price && Objects.equals(name, that.name) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, imageUrl, price);
    }
}
