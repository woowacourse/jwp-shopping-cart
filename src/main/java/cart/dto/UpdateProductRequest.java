package cart.dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class UpdateProductRequest {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private final String name;

    @NotBlank(message = "이미지 경로는 공백일 수 없습니다.")
    private final String imageUrl;

    @Range(min = 0, max = 1_000_000_000, message = "가격은 10억 이하의 음이 아닌 정수여야 합니다.")
    private final int price;

    public UpdateProductRequest(final String name, final String imageUrl, final int price) {
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
