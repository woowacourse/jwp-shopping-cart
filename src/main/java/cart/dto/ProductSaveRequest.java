package cart.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductSaveRequest {
    @NotBlank(message = "상품의 이름이 입력되지 않았습니다.")
    @Size(min = 1, max = 20, message = "{min}글자 이상 {max}글자 이하로만 입력가능 합니다.")
    private final String name;

    @NotNull(message = "상품의 가격을 입력해주세요.")
    @Range(min = 10, max = 1_000_000, message = "상품 금액은 {min}원 이상 {max}이하의 정수만 입력가능 합니다.")
    private final Integer price;

    @NotBlank(message = "상품의 사진을 등록해주세요.")
    private final String imgUrl;

    public ProductSaveRequest(String name, Integer price, String imgUrl) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
