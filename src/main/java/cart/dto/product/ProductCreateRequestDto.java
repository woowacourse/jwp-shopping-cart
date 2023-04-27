package cart.dto.product;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class ProductCreateRequestDto {

    @NotBlank(message = "상품명은 공백일 수 없습니다.")
    @Length(max = 10, message = "상품명은 최대 10자까지 가능합니다.")
    private String name;

    @NotNull(message = "가격은 공백일 수 없습니다.")
    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
    private Integer price;

    @NotNull(message = "상품 이미지 url을 넣어주세요.")
    private String imgUrl;

    private ProductCreateRequestDto() {
    }

    public ProductCreateRequestDto(final String name, final Integer price, final String imgUrl) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
