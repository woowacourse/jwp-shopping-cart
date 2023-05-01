package cart.dto;

import cart.entity.Product;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductRequestDto {

    @NotBlank(message = "상품 이름은 빈 값이 될 수 없습니다.")
    private final String name;

    @NotBlank(message = "상품 이미지는 빈 이미지가 될 수 없습니다.")
    private final String imageUrl;

    @Min(value = 0, message = "상품 가격은 음수가 될 수 없습니다.")
    private final int price;

    public ProductRequestDto(final String name, final String imageUrl, final int price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product makeProduct() {
        return new Product(
            this.name,
            this.imageUrl,
            this.price
        );
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
