package cart.dto;

import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class ProductRequest {

    @ApiModelProperty(value = "상품 이름", example = "상품 이름")
    @NotBlank(message = "상품의 이름을 입력해주세요.")
    private final String name;

    @ApiModelProperty(value = "상품 가격", example = "상품 가격")
    @Range(min = 0, max = 100_000_000, message = "0원 이상 1억 이하의 가격을 입력해주세요.")
    private final int price;

    @ApiModelProperty(value = "상품 이미지 URL", example = "상품 이미지 URL")
    @URL(protocol = "https", message = "https로 시작하는 URL 주소를 사용해주세요.")
    @Length(max = 2083, message = "URL은 최대 {max}자 까지 가능합니다.")
    @NotBlank(message = "상품의 사진 url을 입력해주세요.")
    private final String imageUrl;

    public ProductRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
