package cart.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductRequest {

    @NotNull(message = "이미지 url은 비어있을 수 없습니다.")
    private String image;

    @NotNull(message = "이름은 비어있을 수 없습니다.")
    private String name;

    @NotNull(message = "가격은 비어있을 수 없습니다.")
    @Min(value = 0, message = "가격은 {min} 이상이여야 합니다.")
    @Max(value = 1_000_000_000, message = "가격은 {max} 이하여야 합니다.")
    private long price;

    ProductRequest() {
    }

    public ProductRequest(String image, String name, long price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }
}
