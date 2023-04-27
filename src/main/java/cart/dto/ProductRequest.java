package cart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductRequest {

    @NotNull(message = "이미지 url은 비어있을 수 없습니다.")
    private final String image;

    @NotNull(message = "이름은 비어있을 수 없습니다.")
    private final String name;

    @NotNull(message = "가격은 비어있을 수 없습니다.")
    @Size(min = 0, max = 1_000_000_000, message = "가격은 {min} 이상 {max} 이하여야 합니다.")
    private final long price;

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
