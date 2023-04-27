package cart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ProductEditRequestDto {

    @PositiveOrZero(message = "상품의 ID값을 입력해주세요.")
    private Long id;

    @NotBlank(message = "상품명은 공백일 수 없습니다.")
    private String name;

    @NotNull(message = "가격은 공백일 수 없습니다.")
    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
    private int price;

    @NotNull(message = "상품 이미지 url을 넣어주세요.")
    private String imgUrl;

    public ProductEditRequestDto() {

    }

    public Long getId() {
        return id;
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
