package cart.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ProductRequest {

    @NotNull(message = "이미지 url은 비어있을 수 없습니다.")
    private String imageUrl;

    @NotNull(message = "이름은 비어있을 수 없습니다.")
    @Size(min = 1, max = 50)
    private String name;

    @NotNull(message = "가격은 비어있을 수 없습니다.")
    @Positive(message = "가격은 0 이상이어야 합니다.")
    @Max(value = 1_000_000_000, message = "가격은 최대 10억까지 가능합니다.")
    private Integer price;

    public ProductRequest(String imageUrl, String name, Integer price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }

    public ProductRequest() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}
